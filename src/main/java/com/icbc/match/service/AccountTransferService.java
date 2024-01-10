package com.icbc.match.service;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.icbc.match.api.service.icbc.*;
import com.icbc.match.entity.*;
import com.icbc.match.exception.TransactionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.icbc.apip.exception.InvokerException;
import com.icbc.apip.invoker.Invokers;
import com.icbc.match.config.ApiConstants;
import com.icbc.match.entry.RetEntry;
import com.icbc.match.entry.SecurityUser;
import com.icbc.match.mapper.AccountTranlogMapper;
import com.icbc.match.mapper.AccountUserMapper;
import com.icbc.match.security.IcbcSmService;
import com.icbc.match.vo.TransferVo;

import cn.hutool.core.util.IdUtil;
import lombok.extern.slf4j.Slf4j;


@Service
@Slf4j
public class AccountTransferService {

    @Autowired
    private SettlementAccountBranchRechargeV1Service settlementAccountBranchRechargeV1Service;

    @Autowired
    private SettlementAccountBranchQueryOpenV1Service settlementAccountBranchQueryOpenV1Service;

    @Autowired
    private SettlementAccountBranchQueryApplyV1Service settlementAccountBranchQueryApplyV1Service;
    @Autowired
    private AccountUserMapper accountUserMapper;

    @Autowired
    private BranchBalanceQueryV1Service balanceQueryV1Service;

    @Autowired
    private BranchWithdrawV1Service branchWithdrawV1Service;

    @Autowired
    private AccountTranlogMapper accountTranlogMapper;

    @Autowired
    private IcbcSmService icbcSmService;

    /**
     * 充值提现模块
     *
     * @param transferVo
     * @return
     */

    public RetEntry transfer(TransferVo transferVo) {

        SecurityUser user = gainSecurityUser();
        if (user == null) {
            return new RetEntry("-1", "系统异常请稍后再试。");
        }

        if ("2".equals(transferVo.getType())) {
            RetEntry result = queryBalance(user.getUuid());
            if (!"0".equals(result.getRetCode())) {
                return result;
            }
            String balanceFac = (String) result.getParam("balance");
            Double balanceFact = Double.parseDouble(balanceFac);
            Double passAmount = Double.parseDouble(transferVo.getAmount());
            if (balanceFact < passAmount) {
                return new RetEntry("-1", "余额不足");
            }
        }

        RetEntry entry = transfer(transferVo.getBindNo(), transferVo.getAmount(), transferVo.getAmount(),
                user.getUuid());

        return entry;

    }

    /**
     * 3.查询余额
     *
     * @param userId
     * @return
     */
    public RetEntry queryBalance(String userId) {

        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("user_id", userId);
        AccountUser accountUser = accountUserMapper.selectOne(queryWrapper);
        if (accountUser == null) {
            return new RetEntry("-1", "未查询到账户信息");
        }

        String mediumId = accountUser.getMediumId();// 二类户账号

        Map<String, String> map = new HashMap();
        map.put("mediumId", mediumId);
        map = icbcSmService.encrypt(map);
        String romdom = "FHJS025" + IdUtil.simpleUUID().substring(0, 6);
        SimpleDateFormat sdf2 = new SimpleDateFormat("HH:mm:ss");
        Date now = Calendar.getInstance().getTime();
        String time = sdf2.format(now);

        BranchBalanceQuery branchBalancequery = new BranchBalanceQuery();
        branchBalancequery.setCorp_no(ApiConstants.CORP_NO);
        branchBalancequery.setTrx_acc_date(ApiConstants.ACC_DATE);
        branchBalancequery.setTrx_acc_time(time);
        branchBalancequery.setCorp_date(ApiConstants.ACC_DATE);
        branchBalancequery.setCorp_serno(romdom);
        branchBalancequery.setOut_service_code("querybalance");
        branchBalancequery.setMedium_id(map.get("mediumId"));
        branchBalancequery.setCcy(1);
        branchBalancequery.setCall_type("API");
        branchBalancequery.setSecret_key(map.get("secretKey"));

        Map<String, Object> resp = balanceQueryV1Service.branchBalancequery(branchBalancequery);

        if (resp == null) {
            return new RetEntry("-1", "接口调用异常");
        }

        int returnCode = -1;
        String returnMsg = "";
        try {
            returnCode = Invokers.getReturnCode(resp);
            returnMsg = Invokers.getReturnMsg(resp);
        } catch (InvokerException e) {
            log.error(e.getMessage());
            throw new TransactionException(String.valueOf(returnCode), returnMsg);
        }

        if (returnCode != 0) {
            return new RetEntry("-1", "接口调用异常");
        }

        String balance = (String) resp.get("account_balance");

        BigDecimal bd = new BigDecimal(balance);
        bd = bd.divide(new BigDecimal(100));

        DecimalFormat format = new DecimalFormat("0.00");
        balance = format.format(bd.doubleValue());

        return RetEntry.getOneOkRetEntry().addParam("mediumId", mediumId).addParam("balance", balance);

    }

    /**
     * @param bindMedium
     * @param amount
     * @param type
     * @param userId
     * @return
     */
    public RetEntry transfer(String bindMedium, String amount, String type, String userId) {
        // 参数校验
        if (!"1".equals(type) && !"2".equals(type)) {
            return new RetEntry("-1", "转账类型错误");
        }
        if (!isBindNo(bindMedium)) {
            return new RetEntry("-1", "绑卡卡号位数异常");
        }
        if (!isNumber(amount)) {
            return new RetEntry("-1", "请检查，金额输入非法");
        }
        // 校验输入金额是否为负数
        if (Double.parseDouble(amount) <= 0) {
            return new RetEntry("-1", "金额输入非法");
        }

        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("USER_ID", userId);
        AccountUser accountUser = accountUserMapper.selectOne(queryWrapper);

        // 此处调用转账方法，获取到交易编码serno
        RetEntry transferEn = null;

        if ("1".equals(type) || "2".equals(type)) {
            // 1、2---充值提现模块
            transferEn = this.transferExecute(type, bindMedium, amount, "camsAppId");
        }
        // 保存转账记录
        AccountTranlog tranlog = new AccountTranlog();
        tranlog.setSerno((String) transferEn.getParam("serno"));
        tranlog.setUserId(gainSecurityUser().getUuid());
        tranlog.setMediumId((String) transferEn.getParam("mediumId"));
        tranlog.setBindNo(bindMedium);
        tranlog.setAmount(amount);
        tranlog.setTransCode(type);
        tranlog.setRetCode(transferEn.getRetCode());
        tranlog.setRetMsg(transferEn.getRetMsg());
        tranlog.setTransDate(new Date());
        accountTranlogMapper.insert(tranlog);

        // 结算类交易查询
        String serno = "FHJS025" + IdUtil.simpleUUID().substring(0, 6);
        log.info("@@FHJS025:" + serno);

        SettlementAccountBranchQueryApply settlementAccountBranchQueryApply = new SettlementAccountBranchQueryApply();
        settlementAccountBranchQueryApply.setCorpNo(ApiConstants.CORP_NO);
        settlementAccountBranchQueryApply.setCorpSerno(serno);
        settlementAccountBranchQueryApply.setOutServiceCode("queryapply");
        settlementAccountBranchQueryApply.setOriCorpSerno(accountUser.getOrigSerno());
        Map result = settlementAccountBranchQueryApplyV1Service.settlementAccountBranchQueryApply(settlementAccountBranchQueryApply);


        if (result == null) {
            return new RetEntry("-1", "接口调用异常");
        }
        try {
            int returnCode = Invokers.getReturnCode(result);
            if (returnCode != 0) {
                return new RetEntry("-1", Invokers.getReturnMsg(result));
            }
            int crStusts = (Integer) result.get("cr_stusts");
            int appStatus = (Integer) result.get("app_status");// 4-成功
            log.info("@@crStusts=" + crStusts + ";appStatus=" + appStatus);
            if (!(2 == crStusts) || !(4 == appStatus)) {
                String errorMessage = (String) result.get("error_message");
                log.info("此记录转账异常errorMessage=" + errorMessage);
                return new RetEntry("-1", errorMessage);
            }
            return RetEntry.getOneOkRetEntry();

        } catch (InvokerException e) {
            return new RetEntry("-1", "转账接口调用异常");
        }

    }

    /**
     * 提现
     *
     * @param type
     * @param bindMedium
     * @param amount
     * @param camsAppId
     * @return
     */
    public RetEntry transferExecute(String type, String bindMedium, String amount, String camsAppId) {
        if (!isNumber(amount)) {
            return new RetEntry("-1", "金额输入非法");
        }
        try {
            BigDecimal bd = new BigDecimal(amount);
            bd = bd.multiply(new BigDecimal(100));
            amount = String.valueOf(bd.intValue());
        } catch (Exception e) {
            return new RetEntry("-1", "金额输入非法");
        }

        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("USER_ID", gainSecurityUser().getUuid());
        AccountUser accountUser = accountUserMapper.selectOne(queryWrapper);
        if (accountUser == null) {
            return new RetEntry("-1", "未查询到账户信息");
        }

        String mediumId = accountUser.getMediumId();// 二类户账号

        String serno = "FHJS025" + IdUtil.simpleUUID().substring(0, 6);

        // 转账接口调用模块
        Map<String, Object> resp = null;
        SimpleDateFormat sdf2 = new SimpleDateFormat("HH:mm:ss");
        Date now = Calendar.getInstance().getTime();
        String time = sdf2.format(now);
        //1 充值  2 提现
        if ("1".equals(type)) {
            Map<String, String> map = new HashMap();
            map.put("mediumId", mediumId);
            map.put("bindMedium", bindMedium);
            map = icbcSmService.encrypt(map);
            SettlementAccountBranchRecharge settlementAccountBranchRecharge = new SettlementAccountBranchRecharge();
            settlementAccountBranchRecharge.setCorpNo(ApiConstants.CORP_NO);
            settlementAccountBranchRecharge.setTrxAccDate(ApiConstants.ACC_DATE);
            settlementAccountBranchRecharge.setTrxAccTime(time);
            settlementAccountBranchRecharge.setCorpDate(ApiConstants.ACC_DATE);
            settlementAccountBranchRecharge.setCorpSerno(serno);
            settlementAccountBranchRecharge.setOutServiceCode("recharge");
            settlementAccountBranchRecharge.setMediumId(map.get("mediumId"));
            settlementAccountBranchRecharge.setBindMedium(map.get("bindMedium"));
            settlementAccountBranchRecharge.setCcy(001);
            settlementAccountBranchRecharge.setCashExFlag(0);
            settlementAccountBranchRecharge.setAmount(amount);
            settlementAccountBranchRecharge.setSummary("充值");
            settlementAccountBranchRecharge.setSecretKey(map.get("secretKey"));
            resp = settlementAccountBranchRechargeV1Service.execute(settlementAccountBranchRecharge);

        } else if ("2".equals(type)) {

            System.out.println("romdom:" + serno);

            Map<String, String> map = new HashMap();
            map.put("mediumId", mediumId);
            map.put("bindMedium", bindMedium);
            map = icbcSmService.encrypt(map);

            BranchWithdraw branchWithdraw = new BranchWithdraw();
            branchWithdraw.setCorp_no(ApiConstants.CORP_NO);
            branchWithdraw.setTrx_acc_date(ApiConstants.ACC_DATE);
            branchWithdraw.setTrx_acc_time(time);
            branchWithdraw.setCorp_date(ApiConstants.ACC_DATE);
            branchWithdraw.setCorp_serno(serno);
            branchWithdraw.setOut_service_code("withdraw");
            branchWithdraw.setMedium_id(map.get("mediumId"));
            branchWithdraw.setBind_medium(map.get("bindMedium"));
            branchWithdraw.setCcy(1);
            branchWithdraw.setCash_ex_flag(0);
            branchWithdraw.setAmount(amount);
            branchWithdraw.setSummary("提现");
            branchWithdraw.setCall_type("API");
            branchWithdraw.setSecret_key(map.get("secretKey"));

            resp = branchWithdrawV1Service.branchWithdraw(branchWithdraw);

        } else {
            return new RetEntry("-1", "类型异常异常01");
        }

        if (resp == null) {
            return new RetEntry("-1", "接口调用异常01");
        }
        try {
            int returnCode = Invokers.getReturnCode(resp);
            if (returnCode != 0) {
                return new RetEntry("-1", Invokers.getReturnMsg(resp));
            }
            return RetEntry.getOneOkRetEntry().addParam("serno", serno).addParam("mediumId", mediumId);
        } catch (InvokerException e) {
            return new RetEntry("-1", "接口调用异常99");
        }
    }

    /**
     * 获取token
     *
     * @return
     */
    private SecurityUser gainSecurityUser() {

        UsernamePasswordAuthenticationToken authentication = (UsernamePasswordAuthenticationToken) SecurityContextHolder
                .getContext().getAuthentication();
        SecurityUser user = (SecurityUser) authentication.getPrincipal();

        return user;
    }

    /**
     * 2.2 绑卡卡号校验，19位数字
     *
     * @param bindNo
     * @return
     */
    private static boolean isBindNo(String bindNo) {
        Pattern pattern = Pattern.compile("^\\d{16,19}$");
        Matcher isNum = pattern.matcher(bindNo);
        if (isNum.matches()) {
            return true;
        }
        return false;
    }

    /**
     * 2.3金额校验，保留两位小数
     *
     * @param amount
     * @return
     */
    private static boolean isNumber(String amount) {
        int index = amount.indexOf(".");
        int length = amount.length();
        index++;
        if (length - index > 2) {
            return false;
        }
        return true;
    }

}
