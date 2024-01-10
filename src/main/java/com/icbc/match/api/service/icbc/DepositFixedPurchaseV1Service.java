package com.icbc.match.api.service.icbc;

import com.icbc.apip.invoker.Invoker;
import com.icbc.match.api.client.ApiClientService;
import com.icbc.match.config.ApiConstants;
import com.icbc.match.entity.DepositPurchase;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * 定期产品API接口服务
 * 接口名称	接口资源URL
 * 定期产品购买	                /deposit/fixed/purchase/V1
 */
//@Service
@Slf4j
public class DepositFixedPurchaseV1Service {


    @Autowired
    private ApiClientService apiClientService;


    /**
     * @param depositPurchase
     * @return
     */
    public Map<String, Object> depositFixedPurchase(DepositPurchase depositPurchase) {


        Map<String, Object> params = new HashMap<String, Object>();
        params.put("app_id", depositPurchase.getAppId());
        params.put("workdate", depositPurchase.getWorkdate());
        params.put("worktime", depositPurchase.getWorktime());
        params.put("external_event_no", depositPurchase.getExternalEventNo());
        params.put("out_service_code", depositPurchase.getOutServiceCode());
        params.put("medium_id", depositPurchase.getMediumId());
        params.put("term", depositPurchase.getTerm());
        params.put("prod_code", depositPurchase.getProdCode());
        params.put("lgldoctp", depositPurchase.getLgldoctp());
        params.put("name", depositPurchase.getName());
        params.put("cash_ex_flag", depositPurchase.getCashExFlag());
        params.put("amount", depositPurchase.getAmount());
        params.put("ccy", depositPurchase.getCcy());
        params.put("secret_key", depositPurchase.getSecretKey());
        log.info("DepositFixedPurchase params:{}", params);
        Map<String, Object> response = null;
        response = apiClientService.syncInvoke(ApiConstants.API_DEPOSIT_FIXED_PURCHASE_V1, Invoker.HttpMethodType.POST, params);
        if (response != null) {
            log.info("DepositFixedPurchase value:{}", response);
        }
        return response;
    }
}
