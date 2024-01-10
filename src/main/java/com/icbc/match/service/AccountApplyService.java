package com.icbc.match.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.gson.Gson;
import com.icbc.apip.exception.InvokerException;
import com.icbc.apip.invoker.Invokers;
import com.icbc.match.config.ApiConstants;
import com.icbc.match.entity.AccountBindCard;
import com.icbc.match.entity.AccountCard;
import com.icbc.match.entity.AccountSms;
import com.icbc.match.entity.AccountUser;
import com.icbc.match.entity.SettlementAccountBranchOpen;
import com.icbc.match.entity.SettlementAccountBranchQueryOpen;
import com.icbc.match.entity.SettlementAccountBranchScodeSend;
import com.icbc.match.entity.SettlementAccountBranchScodeVerify;
import com.icbc.match.entry.RetEntry;
import com.icbc.match.exception.TransactionException;
import com.icbc.match.mapper.AccountBindCardMapper;
import com.icbc.match.mapper.AccountCardMapper;
import com.icbc.match.mapper.AccountSmsMapper;
import com.icbc.match.mapper.AccountUserMapper;
import com.icbc.match.security.IcbcSmService;
import com.icbc.match.snowflake.SnowflakeIdService;
import com.icbc.match.utils.TokenUtil;
import com.icbc.match.vo.CaptchaSendVo;
import com.icbc.match.vo.CaptchaVerifyVo;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class AccountApplyService {


    @Autowired
    private AccountUserMapper accountUserMapper;
    @Autowired
    private AccountCardMapper accountCardMapper;
    @Autowired
    private AccountBindCardMapper accountBindCardMapper;

    @Autowired
    private AccountSmsMapper accountSmsMapper;

    @Autowired
    private SnowflakeIdService snowflakeIdService;



    /**
     * 调用开卡服务（发送验证码）
     *
     * @param captchaSendVo
     * @return
     */

   /* public RetEntry accountOpen(CaptchaSendVo captchaSendVo) {

        String userId = TokenUtil.getUserId();
        Map<String, Object> result = new HashMap();

        // 查询之前存入信息
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("USER_ID", userId);
        AccountUser accountUser = accountUserMapper.selectOne(queryWrapper);

        if ("0".equals(captchaSendVo.getFlag())) { // 首次开户+验证码
            SettlementAccountBranchOpen settlementAccountBranchOpen = new SettlementAccountBranchOpen();
            String corpSerno = snowflakeIdService.nextId();

            String mediumId = captchaSendVo.getBindMedium();

            Gson gson = new Gson();

            Map<String, String> map = new HashMap();
            map.put("certNo", captchaSendVo.getCertNo());
            map.put("custName", captchaSendVo.getCustName());
            map.put("bindMedium", mediumId);
            map.put("mobile", captchaSendVo.getMobileNo());
            map = icbcSmService.encrypt(map);

            log.info(gson.toJson(icbcSmService.encrypt(map)));
            String serNo = ApiConstants.FHJS025 + IdUtil.simpleUUID().substring(0, 6);
            String cisNo = IdUtil.simpleUUID();//合作方客户号

            settlementAccountBranchOpen.setCorpNo(ApiConstants.CORP_NO);
            settlementAccountBranchOpen.setTrxAccDate(ApiConstants.ACC_DATE);
            settlementAccountBranchOpen.setTrxAccTime(DateUtil.formatTime(DateUtil.date()));
            settlementAccountBranchOpen.setCorpDate(ApiConstants.ACC_DATE);
            settlementAccountBranchOpen.setCorpSerno(serNo);
            settlementAccountBranchOpen.setOutServiceCode("openaccount");
            settlementAccountBranchOpen.setCorpCisNo(cisNo);
            settlementAccountBranchOpen.setCorpMediumId(serNo);
            settlementAccountBranchOpen.setBindMedium(map.get("bindMedium"));
            settlementAccountBranchOpen.setCertType(0);
            settlementAccountBranchOpen.setCertNo(map.get("certNo"));
            settlementAccountBranchOpen.setCustName(map.get("custName"));
            settlementAccountBranchOpen.setMobileNo(map.get("mobile"));
            settlementAccountBranchOpen.setGender(2);
            settlementAccountBranchOpen.setOccupation(3);
            settlementAccountBranchOpen.setForeverFlag(0);
            settlementAccountBranchOpen.setSignDate(accountUser.getSignDate()); // 签发日期
            settlementAccountBranchOpen.setValidityPeriod(accountUser.getValidityPeriod()); // 失效日期
            settlementAccountBranchOpen.setNationality(156);
            settlementAccountBranchOpen.setTaxDeclarationFlag(1);
            settlementAccountBranchOpen.setSecretKey(map.get("secretKey"));
            settlementAccountBranchOpen.setAddress(captchaSendVo.getAddress());

            // 存储绑卡信息
            AccountBindCard accountBindCard = new AccountBindCard();
            accountBindCard.setBindId(snowflakeIdService.nextId());
            accountBindCard.setUserId(userId);
            accountBindCard.setBindNo(mediumId);
            accountBindCard.setMobileNo(TokenUtil.getMobile());
            accountBindCard.setBindTime(DateUtil.date());
            accountBindCard.setOrigSerno(serNo);
            accountBindCardMapper.insert(accountBindCard);

            result = settlementAccountBranchOpenV2Service.execute(settlementAccountBranchOpen);

        } else if ("1".equals(captchaSendVo.getFlag())) {// 首次发送验证码失败，再次发送验证码
            log.info("再次请求发送验证码");


            SettlementAccountBranchScodeSend settlementAccountBranchScodeSend = new SettlementAccountBranchScodeSend();
            settlementAccountBranchScodeSend.setCorpNo(ApiConstants.CERT_NO);
            settlementAccountBranchScodeSend.setTrxAccDate(ApiConstants.ACC_DATE);
            settlementAccountBranchScodeSend.setTrxAccTime(DateUtil.formatTime(DateUtil.date()));
            settlementAccountBranchScodeSend.setCorpDate(ApiConstants.ACC_DATE);
            settlementAccountBranchScodeSend.setCorpSerno(IdUtil.simpleUUID());
            settlementAccountBranchScodeSend.setOutServiceCode("openaccount");
            settlementAccountBranchScodeSend.setCorpSernoOriginal(accountUser.getOrigSerno());


            result = settlementAccountBranchScodeSendV1Service.execute(settlementAccountBranchScodeSend);

        } else {
            throw new TransactionException("-1", "flag标识错误");
        }
        int returnCode = 0;
        String returnMsg = "";
        try {
            returnCode = Invokers.getReturnCode(result);
            returnMsg = Invokers.getReturnMsg(result);
        } catch (InvokerException e) {
            log.error(e.getMessage());
        }
        if (returnCode != 0) {
            throw new TransactionException(String.valueOf(returnCode), returnMsg);
        }
        String serno = result.get("corp_serno").toString();
        String smsSendNo = result.get("sms_send_no").toString();
        // 存储验证码,流水号
        AccountSms accountSms = new AccountSms();
        accountSms.setUserId(userId);
        accountSms.setSerno(serno);
        accountSms.setSmsSendNo(smsSendNo);
        accountSms.setCreateTime(DateUtil.date());

        accountSmsMapper.insert(accountSms);

        return RetEntry.getOneOkRetEntry();
    }

    *//**
     * 校验验证码，并开户
     *
     * @param captchaVerifyVo
     * @return
     *//*
    public RetEntry verifyCaptcha(CaptchaVerifyVo captchaVerifyVo) {
        String userId = TokenUtil.getUserId();
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("USER_ID", userId);
        queryWrapper.orderByDesc("CREATE_TIME");

        AccountSms accountSms = accountSmsMapper.selectOne(queryWrapper);
        AccountUser accountUser = accountUserMapper.selectOne(queryWrapper);

        // 3.调用银行校验接口
        SettlementAccountBranchScodeVerify settlementAccountBranchScodeVerify = new SettlementAccountBranchScodeVerify();
        settlementAccountBranchScodeVerify.setCorpNo(ApiConstants.CORP_NO);
        settlementAccountBranchScodeVerify.setTrxAccDate(ApiConstants.ACC_DATE);
        settlementAccountBranchScodeVerify.setTrxAccTime(DateUtil.formatTime(DateUtil.date()));
        settlementAccountBranchScodeVerify.setCorpDate(ApiConstants.ACC_DATE);
        settlementAccountBranchScodeVerify.setCorpSerno(ApiConstants.FHJS025 + IdUtil.simpleUUID().substring(0, 6));
        settlementAccountBranchScodeVerify.setOutServiceCode("scodeverify");
        settlementAccountBranchScodeVerify.setCorpSernoOriginal(accountSms.getOrigSerno());
        settlementAccountBranchScodeVerify.setSmsSendNo(accountSms.getSmsSendNo());
        settlementAccountBranchScodeVerify.setSmsScode(captchaVerifyVo.getCaptcha());

        Map<String, Object> result = settlementAccountBranchScodeVerifyV1Service
                .execute(settlementAccountBranchScodeVerify);
        String returnCode = result.get("return_code").toString();
        String mediumId = result.get("medium_id").toString();
        String secretKey = result.get("secret_key").toString();


        //解密保存二类户卡号
        if ("0".equals(returnCode)) {
            Map map = new HashMap();
            map.put("secretKey", secretKey);
            map.put("medium_id", mediumId);
            map = icbcSmService.decrypt(map);


            // 保存成功开卡信息
            AccountCard accountCard = new AccountCard();
            accountCard.setUserId(userId);
            accountCard.setMediumId(map.get("medium_id").toString());
            accountCard.setUserId(userId);
            accountCard.setOrigSerno(accountSms.getSerno());
            accountCard.setCreateTime(DateUtil.date());
            accountCard.setOpenDate(DateUtil.formatDate(DateUtil.date()));
            accountCard.setOpenTime(DateUtil.formatTime(DateUtil.date()));
            accountCard.setStatus("0");
            accountCardMapper.insert(accountCard);


        } else {

            // 调用查询开卡结果接口
            SettlementAccountBranchQueryOpen settlementAccountBranchQueryOpen = new SettlementAccountBranchQueryOpen();
            settlementAccountBranchQueryOpen.setCorpNo(ApiConstants.CORP_NO);
            settlementAccountBranchQueryOpen.setOutServiceCode("queryopenbook");
            settlementAccountBranchQueryOpen.setOpenCorpSerno(accountUser.getOrigSerno());
            settlementAccountBranchQueryOpen.setCallType("API");

            Map<String, Object> resultMap = settlementAccountBranchQueryOpenV1Service
                    .execute(settlementAccountBranchQueryOpen);

            Map map = new HashMap();
            map.put("mediumId", resultMap.get("medium_id"));
            map.put("secretKey", resultMap.get("secret_key"));
            map = icbcSmService.decrypt(map);


            int returnCodeQueryOpen = -1;
            String returnMsg = "";
            try {
                returnCodeQueryOpen = Invokers.getReturnCode(resultMap);
                returnMsg = Invokers.getReturnMsg(resultMap);
            } catch (InvokerException e) {
                log.error(e.getMessage());
                throw new TransactionException(String.valueOf(returnCodeQueryOpen), returnMsg);
            }

            // 再次查询开卡结果，如果成功更新卡号，失败返回报错
            if (returnCodeQueryOpen == 0) {
                // 保存成功开卡信息
                AccountCard accountCard = new AccountCard();
                accountCard.setUserId(userId);
                accountCard.setMediumId(map.get("medium_id").toString());
                accountCard.setOrigSerno(accountSms.getSerno());
                accountCard.setCreateTime(DateUtil.date());
                accountCard.setOpenDate(DateUtil.formatDate(DateUtil.date()));
                accountCard.setOpenTime(DateUtil.formatTime(DateUtil.date()));
                accountCard.setStatus("0");
                accountCardMapper.insert(accountCard);
            } else {
                throw new TransactionException(String.valueOf(returnCode), returnMsg);
            }

        }


        return RetEntry.getOneOkRetEntry().addParam("custName", accountUser.getCustName())
                .addParam("userId", accountUser.getUserId()).addParam("mediumId", mediumId);

    }*/

}
