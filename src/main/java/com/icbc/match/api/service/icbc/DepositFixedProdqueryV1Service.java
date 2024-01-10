package com.icbc.match.api.service.icbc;


import com.icbc.apip.invoker.Invoker;
import com.icbc.match.api.client.ApiClientService;
import com.icbc.match.config.ApiConstants;
import com.icbc.match.entity.DepositFixedProdquery;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

//@Service
@Slf4j
public class DepositFixedProdqueryV1Service {


    @Autowired
    private ApiClientService apiClientService;


    public Map<String, Object> depositFixedProdquery(DepositFixedProdquery depositFixedProdquery) {
        Map<String, Object> response = null;
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("cm_external_event_no", depositFixedProdquery.getCmExternalEventNo());
        params.put("cm_app_id", depositFixedProdquery.getCmAppId());
        params.put("cm_workdate", depositFixedProdquery.getCmWorkdate());
        params.put("cm_worktime", depositFixedProdquery.getCmWorktime());
        params.put("qry_type", depositFixedProdquery.getQryType());
        params.put("start", depositFixedProdquery.getStart());
        params.put("rows", depositFixedProdquery.getRows());
        params.put("prod_code", depositFixedProdquery.getProdCode());
        log.info("params:{}", params);
        response = apiClientService.syncInvoke(ApiConstants.API_DEPOSIT_FIXED_PRODQUERY_V1, Invoker.HttpMethodType.POST, params);
        if (response != null) {
            log.debug("value:{}", response);
        }
        return response;
    }

}
