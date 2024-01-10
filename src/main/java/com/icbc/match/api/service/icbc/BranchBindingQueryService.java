package com.icbc.match.api.service.icbc;


import com.icbc.match.api.client.ApiClientService;
import com.icbc.match.config.ApiConstants;
import com.icbc.match.entity.BranchBindingQuery;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class BranchBindingQueryService {
    @Autowired
    private ApiClientService apiClientService;


    /**
     * 结算账户绑卡信息查询接口(分行)
     *
     * @param branchBindingQuery
     * @return
     */
    public Map<String, Object> branchBindingQuery(BranchBindingQuery branchBindingQuery) {

        Map<String, Object> response = null;
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("corp_no", branchBindingQuery.getCorpNo());
        params.put("trx_acc_date", branchBindingQuery.getTrxAccDate());
        params.put("trx_acc_time", branchBindingQuery.getTrxAccTime());
        params.put("corp_date", branchBindingQuery.getCorpDate());
        params.put("corp_serno", branchBindingQuery.getCorpSerno());
        params.put("out_service_code", branchBindingQuery.getOutServiceCode());
        params.put("medium_id", branchBindingQuery.getMediumId());
        params.put("secret_key", branchBindingQuery.getSecretKey());





        response = apiClientService.syncInvokeReqBodyJson(ApiConstants.API_SETTLEMENT_ACCOUNT_BRANCH_BINDING_QUERY_V1,params);
        if (response != null) {
            log.debug("value:{}", response);
        }
        return response;
    }
}


