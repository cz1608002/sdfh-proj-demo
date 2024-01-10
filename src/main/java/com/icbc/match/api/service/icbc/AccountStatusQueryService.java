package com.icbc.match.api.service.icbc;


import com.icbc.match.api.client.ApiClientService;
import com.icbc.match.config.ApiConstants;
import com.icbc.match.entity.AccountStatusQuery;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class AccountStatusQueryService {
    @Autowired
    private ApiClientService apiClientService;


    /**
     * 结算账户状态查询接口
     *
     * @param accountStatusQuery
     * @return
     */
    public Map<String, Object> accountStatusQuery(AccountStatusQuery accountStatusQuery) {

        Map<String, Object> response = null;
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("out_service_code", accountStatusQuery.getOutServiceCode());
        params.put("corp_serno", accountStatusQuery.getCorpSerno());
        params.put("corp_no", accountStatusQuery.getCorpNo());
        params.put("corp_medium_id", accountStatusQuery.getCorpMediumId());
        params.put("call_type", accountStatusQuery.getCallType());




        response = apiClientService.syncInvokeReqBodyJson(ApiConstants.API_SETTLEMENT_ACCOUNT_STATUS_QUERY_V1,params);
        if (response != null) {
            log.debug("value:{}", response);
        }
        return response;
    }
}
