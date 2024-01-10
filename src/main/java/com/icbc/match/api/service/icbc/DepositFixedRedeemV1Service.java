package com.icbc.match.api.service.icbc;


import com.icbc.apip.invoker.Invoker;
import com.icbc.match.api.client.ApiClientService;
import com.icbc.match.config.ApiConstants;
import com.icbc.match.entity.DepositFixedRedeem;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * 定期存款API接口服务
 * 接口名称	接口资源URL
 * <p>
 * 定期产品赎回       	        /deposit/fixed/redeem/V1
 */
//@Service
@Slf4j
public class DepositFixedRedeemV1Service {

    @Autowired
    private ApiClientService apiClientService;


    public Map<String, Object> depositFixedRedeem(DepositFixedRedeem depositFixedRedeem) {


        Map<String, Object> params = new HashMap<String, Object>();
        params.put("app_id", depositFixedRedeem.getAppId());
        params.put("workdate", depositFixedRedeem.getWorkdate());
        params.put("worktime", depositFixedRedeem.getWorktime());
        params.put("external_event_no", depositFixedRedeem.getExternalEventNo());
        params.put("out_service_code", depositFixedRedeem.getOutServiceCode());
        params.put("medium_id", depositFixedRedeem.getMediumId());
        params.put("prod_code", depositFixedRedeem.getProdCode());
        params.put("ac_code", depositFixedRedeem.getAcCode());
        params.put("cashexf", depositFixedRedeem.getCashexf());
        params.put("amount", depositFixedRedeem.getAmount());
        params.put("curr_type", depositFixedRedeem.getCurrType());
        log.info("DepositFixedRedeem params:{}", params);
        Map<String, Object> response = null;
        response = apiClientService.syncInvoke(ApiConstants.API_DEPOSIT_FIXED_REDEEM_V1, Invoker.HttpMethodType.POST, params);
        if (response != null) {
            log.info("DepositFixedRedeem value:{}", response);
        }
        return response;
    }
}
