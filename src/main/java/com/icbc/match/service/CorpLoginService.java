package com.icbc.match.service;


import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.symmetric.AES;

import com.icbc.match.dict.CorpAuthType;
import com.icbc.match.entity.AccountUser;
import com.icbc.match.entry.Result;
import com.icbc.match.entry.SecurityUser;
import com.icbc.match.exception.TransactionException;
import com.icbc.match.mapper.AccountMerchantMapper;
import com.icbc.match.mapper.AccountUserMapper;
import com.icbc.match.security.JwtTokenService;
import com.icbc.match.utils.AESUtil;
import com.icbc.match.utils.UuidUtil;

import lombok.extern.slf4j.Slf4j;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class CorpLoginService {

    private String encryptType;

    private String encryptKey;

    @Autowired
    private AccountMerchantMapper merchantMapper;

    @Autowired
    private AccountUserMapper accountUserMapper;

    @Autowired
    private AccountUserService userService;

    @Autowired
    private JwtTokenService jwtTokenService;

    private static final String REDIRECT_URL_FORMMER = "?page=%s&token=%s";

    public Result corpLogin(String bizContent) {
        if (StringUtils.isEmpty(bizContent)) {
            throw new TransactionException("-1", "参数非法");
        }

        log.debug("receive corp bizContent:[{}]", bizContent);

        String corpData = AESUtil.aesDecrypt(bizContent, encryptKey); //对api网关报文进行解密,解密失败自动捕捉异常

        log.info("decrypt corp bizContent:[{}]", bizContent);

        JSONObject json = null;
        JSONObject dataJson = null;
		try {
			json = new JSONObject(corpData);
			dataJson = json.getJSONObject("data");
		} catch (JSONException e) {
			e.printStackTrace();
		}



        String orgNo = dataJson.optString("orgNo"); //解析合作方编号
        String callType = dataJson.optString("callType"); //解析授权类型，合作方鉴权|工行鉴权
        String corpUserId = dataJson.optString("orgUserId"); //解析合作方身份标识

        if (StringUtils.isEmpty(orgNo)) {
            throw new TransactionException("-1", "合作方编号非法");
        }

        int count = merchantMapper.checkMerExist(orgNo);
        if (count != 1) {
            throw new TransactionException("-1", "合作方不存在");
        }

        if (CorpAuthType.CORP_AUTH.equals(callType)) {
            return corpAuth(orgNo, corpUserId);
        } else if (CorpAuthType.ICBC_AUTH.equals(callType)) {
            return icbcAuth(orgNo, corpUserId);
        } else {
            throw new TransactionException("-1", "授权类型参数非法");
        }

    }

    /**
     * 合作方鉴权模式
     *
     * @param orgNo      合作方编号
     * @param corpUserId 合作方用户标识
     * @return Result
     */
    private Result corpAuth(String orgNo, String corpUserId) {
        AccountUser user = userService.getUserInfoByUserId(corpUserId); //校验是否存在该用户

        String page;
        if (user == null) {
            user = new AccountUser();
            user.setUserId(UuidUtil.getRandomUUID());
            user.setOrgUserId(corpUserId);
            user.setMerchantId(orgNo);
            accountUserMapper.insert(user); //保存用户

            page = PageType.PAGE_CORP_APPLY;
        } else {
            page = PageType.PAGE_CORP_INDEX;
        }

        SecurityUser securityUser = new SecurityUser();
        securityUser.setUuid(user.getUserId());

        String token = jwtTokenService.generateToken(securityUser); //生成token给前端

        Map<String, Object> param = new HashMap<>();
        String redirect = String.format(REDIRECT_URL_FORMMER, page, token);
        param.put("redirect", redirect);
        return Result.success(param);
    }

    /**
     * 工行鉴权模式
     *
     * @param orgNo      合作方编号
     * @param corpUserId 合作方用户标识
     */
    private Result icbcAuth(String orgNo, String corpUserId) {
        SecurityUser securityUser = new SecurityUser();
        securityUser.setUuid(UuidUtil.getRandomUUID());

        String token = jwtTokenService.generateToken(securityUser); //生成token给前端

        Map<String, Object> param = new HashMap<>();
        String redirect = String.format(REDIRECT_URL_FORMMER, PageType.PAGE_ICBC, token);
        param.put("redirect", redirect);
        param.put("token", token);
        return Result.success(param);
    }

    static class PageType {
        public final static String PAGE_CORP_APPLY = "0";
        public final static String PAGE_CORP_INDEX = "1";
        public final static String PAGE_ICBC = "2";
    }
}
