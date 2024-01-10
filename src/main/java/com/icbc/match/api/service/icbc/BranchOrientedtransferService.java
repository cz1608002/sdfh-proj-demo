package com.icbc.match.api.service.icbc;


import com.icbc.match.api.client.ApiClientService;
import com.icbc.match.config.ApiConstants;
import com.icbc.match.entity.BranchOrientedtransfer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class BranchOrientedtransferService {
    @Autowired
    private ApiClientService apiClientService;


    /**
     * 结算账户定向转账服务接口(分行)
     *
     * @param branchOrientedtransfer
     * @return
     */
    public Map<String, Object> branchOrientedtransfer(BranchOrientedtransfer branchOrientedtransfer) {

        Map<String, Object> response = null;
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("corp_no", branchOrientedtransfer.getCorpNo());
        params.put("trx_acc_date", branchOrientedtransfer.getTrxAccDate());
        params.put("trx_acc_time", branchOrientedtransfer.getTrxAccTime());
        params.put("corp_date", branchOrientedtransfer.getCorpDate());
        params.put("corp_serno", branchOrientedtransfer.getCorpSerno());
        params.put("out_service_code", branchOrientedtransfer.getOutServiceCode());
        params.put("ccy", branchOrientedtransfer.getCcy());
        params.put("cash_ex_flag", branchOrientedtransfer.getCashExFlag());
        params.put("amount", branchOrientedtransfer.getAmount());
        params.put("oaccno", branchOrientedtransfer.getOaccno());
        params.put("drcrdirection", branchOrientedtransfer.getDrcrdirection());
        params.put("summary", branchOrientedtransfer.getSummary());
        params.put("remarks", branchOrientedtransfer.getRemarks());
        params.put("notify_addr", branchOrientedtransfer.getNotifyAddr());
        params.put("secret_key", branchOrientedtransfer.getSecretKey());
        params.put("omedium_id_hash", branchOrientedtransfer.getOmediumIdHash());
        params.put("cert_type", branchOrientedtransfer.getCertType());
        params.put("cert_no", branchOrientedtransfer.getCertNo());
        params.put("cust_name", branchOrientedtransfer.getCustName());
        params.put("call_type", branchOrientedtransfer.getCallType());

        response = apiClientService.syncInvokeReqBodyJson(ApiConstants.API_SETTLEMENT_ACCOUNT_BRANCH_ORIENTEDTRANSFER_V1,params);
        if (response != null) {
            log.debug("value:{}", response);
        }
        return response;
    }
}



