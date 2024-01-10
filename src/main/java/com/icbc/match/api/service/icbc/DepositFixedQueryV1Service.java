package com.icbc.match.api.service.icbc;

import com.icbc.apip.invoker.Invoker;
import com.icbc.match.api.client.ApiClientService;
import com.icbc.match.config.ApiConstants;
import com.icbc.match.entity.DepositFixedQuery;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Slf4j
//@Service
public class DepositFixedQueryV1Service {
    @Autowired
    private ApiClientService apiClientService;


    /**
     * @param depositFixedQuery 查询余额
     * @return QrcodeGenerateResponseV2
     */
    public Map<String, Object> depositFixedQuery(DepositFixedQuery depositFixedQuery) {

        Map<String, Object> response = null;
        Map<String, Object> params = new HashMap<String, Object>();

        params.put("app_id", depositFixedQuery.getAppId());
        params.put("workdate", depositFixedQuery.getWorkdate());
        params.put("worktime", depositFixedQuery.getWorktime());
        params.put("external_event_no", depositFixedQuery.getExternalEventNo());
        params.put("out_service_code", depositFixedQuery.getOutServiceCode());
        params.put("secret_key", depositFixedQuery.getSecretKey());
        params.put("medium_id", depositFixedQuery.getMediumId());
        params.put("query_flag", depositFixedQuery.getQueryFlag());
        params.put("page_num", depositFixedQuery.getPageNum());
        params.put("acc_no", depositFixedQuery.getAccNo());
        params.put("ac_code", depositFixedQuery.getAcCode());
        params.put("prod_code", depositFixedQuery.getProdCode());
        params.put("fxseq_no", depositFixedQuery.getFxseqNo());
        params.put("cash_ex_flag", depositFixedQuery.getCashExFlag());
        params.put("ccy", depositFixedQuery.getCcy());
        log.debug("params:{}", params);
        response = apiClientService.syncInvoke(ApiConstants.API_DEPOSIT_FIXED_QUERY_V1, Invoker.HttpMethodType.POST, params);
        if (response != null) {
            log.debug("value:{}", response);
        }
        return response;
    }
}
