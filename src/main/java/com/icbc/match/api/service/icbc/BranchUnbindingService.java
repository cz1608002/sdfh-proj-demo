package com.icbc.match.api.service.icbc;


import com.icbc.match.api.client.ApiClientService;
import com.icbc.match.config.ApiConstants;
import com.icbc.match.entity.BranchUnbinding;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class BranchUnbindingService {
    @Autowired
    private ApiClientService apiClientService;


    /**
     * 结算账户解绑维护接口(分行)
     *
     * @param settlementAccountBranchUnbinding
     * @return
     */
    public Map<String, Object> settlementAccountBranchUnbinding(BranchUnbinding settlementAccountBranchUnbinding) {

        Map<String, Object> response = null;
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("corp_no", settlementAccountBranchUnbinding.getCorpNo());
        params.put("trx_acc_date", settlementAccountBranchUnbinding.getTrxAccDate());
        params.put("trx_acc_time", settlementAccountBranchUnbinding.getTrxAccTime());
        params.put("corp_date", settlementAccountBranchUnbinding.getCorpDate());
        params.put("corp_serno", settlementAccountBranchUnbinding.getCorpSerno());
        params.put("out_service_code", settlementAccountBranchUnbinding.getOutServiceCode());
        params.put("medium_id", settlementAccountBranchUnbinding.getMediumId());
        params.put("mobile_no", settlementAccountBranchUnbinding.getMobileNo());
        params.put("secret_key", settlementAccountBranchUnbinding.getSecretKey());
        params.put("bind_medium", settlementAccountBranchUnbinding.getBindMedium());




        response = apiClientService.syncInvokeReqBodyJson(ApiConstants.API_SETTLEMENT_ACCOUNT_BRANCH_UNBINDING_V1,params);
        if (response != null) {
            log.debug("value:{}", response);
        }
        return response;
    }
}

