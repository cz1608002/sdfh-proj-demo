package com.icbc.match.service;


import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.gson.Gson;
import com.icbc.match.api.service.icbc.AccountStatusQueryService;
import com.icbc.match.config.ApiConstants;
import com.icbc.match.entity.AccountCard;
import com.icbc.match.entity.AccountStatusQuery;
import com.icbc.match.entry.RetEntry;
import com.icbc.match.mapper.AccountCardMapper;
import com.icbc.match.security.IcbcSmService;
import com.icbc.match.utils.TokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Slf4j
@Service
public class AccountInfoService {


    @Autowired
    private AccountStatusQueryService accountStatusQueryService;

    @Autowired
    private IcbcSmService icbcSmService;

    @Autowired
    private AccountCardMapper accountCardMapper;

    public RetEntry accountStatusQuery() {

        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("USER_ID", TokenUtil.getUserId());

        AccountCard accountCard = accountCardMapper.selectOne(queryWrapper);

        Gson gson = new Gson();
        String serno = "FHJS025" + IdUtil.simpleUUID().substring(0, 6);

        AccountStatusQuery accountStatusQuery = new AccountStatusQuery();
        accountStatusQuery.setOutServiceCode("querystatus");
        accountStatusQuery.setCorpNo(ApiConstants.CORP_NO);
        accountStatusQuery.setCorpSerno(serno);
        accountStatusQuery.setCorpMediumId(accountCard.getMediumId());
        accountStatusQuery.setCallType("API");


        Map result = accountStatusQueryService.accountStatusQuery(accountStatusQuery);

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
        result.put("secretKey", result.get("secret_key").toString());
        result.remove("secret_key");
        result = icbcSmService.decrypt(result);

        return RetEntry.getOneOkRetEntry().addParam("card", result);


    }


}
