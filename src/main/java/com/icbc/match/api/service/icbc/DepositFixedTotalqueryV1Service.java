package com.icbc.match.api.service.icbc;

import com.icbc.apip.invoker.Invoker;
import com.icbc.match.api.client.ApiClientService;
import com.icbc.match.config.ApiConstants;
import com.icbc.match.entity.DepositFixedTotalquery;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Slf4j
//@Service
public class DepositFixedTotalqueryV1Service {


    @Autowired
    private ApiClientService apiClientService;


    public Map<String, Object> depositFixedTotalquery(DepositFixedTotalquery depositFixedTotalquery) {

        Map<String, Object> params = new HashMap<String, Object>();

        params.put("cm_external_event_no", depositFixedTotalquery.getCmExternalEventNo());
        params.put("cm_app_id", depositFixedTotalquery.getCmAppId());
        params.put("cm_workdate", depositFixedTotalquery.getCmWorkdate());
        params.put("cm_worktime", depositFixedTotalquery.getCmWorktime());
        params.put("cm_out_service_code", depositFixedTotalquery.getCmOutServiceCode());
        params.put("cm_secret_key", depositFixedTotalquery.getCmSecretKey());
        log.debug("params:{}", params);
        Map<String, Object> response = null;
        response = apiClientService.syncInvoke(ApiConstants.API_DEPOSIT_FIXED_TOTALQUERY_V1, Invoker.HttpMethodType.POST, params);
        if (response != null) {
            log.debug("value:{}", response);
        }
        return response;
    }
}
