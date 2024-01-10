package com.icbc.match.api.service.cop;

import com.icbc.api.IcbcApiException;
import com.icbc.api.request.SettlementAccountUnholdRequestV1;
import com.icbc.api.request.SettlementAccountUnholdTransferRequestV1;
import com.icbc.api.response.SettlementAccountUnholdResponseV1;
import com.icbc.api.response.SettlementAccountUnholdTransferResponseV1;
import com.icbc.match.api.client.ApiClientCopService;
import com.icbc.match.config.ApiConstants;
import com.icbc.match.entry.ResultCode;
import com.icbc.match.exception.TransactionException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class SettlementAccountUnholdtransferV1Service {

    @Autowired
    private ApiClientCopService apiClientCopService;

    @Value("${icbc.api.url}")
    private String apiServer; //API平台服务器


    public SettlementAccountUnholdTransferResponseV1 execute(SettlementAccountUnholdTransferRequestV1.SettlementAccountUnholdTransferRequestV1Biz bizContent) {

        SettlementAccountUnholdTransferRequestV1 request = new SettlementAccountUnholdTransferRequestV1();

        String serviceUrl = apiServer + ApiConstants.API_SETTLEMENT_ACCOUNT_UNHOLDTRANSFER_V1;
        request.setServiceUrl(serviceUrl);
        request.setBizContent(bizContent);
        try {
            SettlementAccountUnholdTransferResponseV1 response = apiClientCopService.execute(request);

            return response;
        } catch (IcbcApiException e) {
            log.error(e.getMessage());
            throw new TransactionException(ResultCode.API_INVOKE_ERROR);
        }
    }
}
