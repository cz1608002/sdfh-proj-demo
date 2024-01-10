package com.icbc.match.controller;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.gson.Gson;
import com.icbc.match.api.service.icbc.AccountStatusQueryService;
import com.icbc.match.config.ApiConstants;
import com.icbc.match.config.Apollo;
import com.icbc.match.entity.AccountCard;
import com.icbc.match.entity.AccountStatusQuery;
import com.icbc.match.mapper.AccountCardMapper;
import com.icbc.match.security.IcbcSmService;
import com.icbc.match.utils.SM2Utils;
import com.icbc.match.utils.Util;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
public class CorpAccountQueryController {
    @Autowired
    private IcbcSmService icbcSmService;
    private String corpSm2PrivateKey;
    @Autowired
    private AccountCardMapper cardMapper;
    @Autowired
    private AccountStatusQueryService statusQueryService;

    @PostMapping("/status/query")
    public Map<String,String> corpAccountQuery(@RequestParam(name = "cosp", required = true, defaultValue = "") String cosp){
        log.info("corpAccountQuery:{}", cosp);
        Map<String,String> ret=new HashMap<>();
        ret.put("return_code","");
        ret.put("return_msg","");
        try {
            JSONObject data = new JSONObject(cosp);
            JSONObject json = new JSONObject(data.get("data").toString());
            log.info("json:{}", json);
            String orgNo = json.optString("orgNo");
            String secretKey=json.optString("secretKey");
            String encryptId = json.optString("id");
            String rs= Util.getHexString(SM2Utils.decryptBase64(Util.hexToByte(corpSm2PrivateKey),secretKey));
            String userId=Util.decryptData_CBC(rs,encryptId);

            QueryWrapper queryWrapper = new QueryWrapper();
            queryWrapper.eq("user_id", userId);

            AccountCard card=cardMapper.selectOne(queryWrapper);
            if(card==null){
                ret.put("return_code","93001");
                ret.put("return_msg","用户不存在");
                return ret;
            }

            String mediumId=card.getMediumId();
            if(StringUtils.isEmpty(mediumId)){
                ret.put("return_code","93002");
                ret.put("return_msg","卡号不存在");
                return ret;
            }
            Gson gson = new Gson();
            String serno = "FHJS025" + IdUtil.simpleUUID().substring(0, 6);
            log.info("@@FHJS025:" + serno);

            AccountStatusQuery accountStatusQuery = new AccountStatusQuery();
            accountStatusQuery.setOutServiceCode("querystatus");
            accountStatusQuery.setCorpNo(ApiConstants.CORP_NO);
            accountStatusQuery.setCorpSerno(serno);
            accountStatusQuery.setCorpMediumId(mediumId);
            accountStatusQuery.setCallType("API");

            Map result = statusQueryService.accountStatusQuery(accountStatusQuery);

            log.info(gson.toJson(result));
            result.remove("cert_type");
            result.remove("bindInfoList");
            result.remove("card_stat");
            result.remove("account_level");
            result.remove("return_msg");
            result.remove("corp_cis_no");
            result.remove("message");
            result.remove("account_org_no");
            result.remove("corp_name");
            result.remove("cert_flag");
            result.remove("cust_cis_no");
            result.remove("return_code");
            result.put("secretKey",result.get("secret_key").toString());
            result.remove("secret_key");
            icbcSmService.decrypt(result);
            log.info(gson.toJson(result));
            if(!"0".equals(result.get("return_code").toString())){
                ret.put("return_code",result.get("return_code").toString());
                ret.put("return_msg",result.get("return_msg").toString());
                return ret;
            }
            ret.put("return_code","0");
            ret.put("return_code","success");
            ret.put("card_stat",result.get("card_stat").toString());
            ret.put("secret_key",result.get("secret_key").toString());
            ret.put("medium_id",result.get("medium_id").toString());



        } catch (Exception e) {
            ret.put("return_code","-1");
            ret.put("return_msg","执行异常");
            log.error("执行异常", e);
        }
        return ret;
    }
}
