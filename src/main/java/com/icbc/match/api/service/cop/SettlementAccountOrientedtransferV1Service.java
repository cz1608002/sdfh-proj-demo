package com.icbc.match.api.service.cop;

import com.icbc.api.IcbcApiException;
import com.icbc.api.request.SettlementAccountOrientedTransferRequestV1;
import com.icbc.api.request.SettlementAccountWithdrawRequestV1;
import com.icbc.api.response.SettlementAccountOrientedTransferResponseV1;
import com.icbc.api.response.SettlementAccountWithdrawResponseV1;
import com.icbc.match.api.client.ApiClientCopService;
import com.icbc.match.config.ApiConstants;
import com.icbc.match.entry.ResultCode;
import com.icbc.match.exception.TransactionException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class SettlementAccountOrientedtransferV1Service {

    @Autowired
    private ApiClientCopService apiClientCopService;

    @Value("${icbc.api.url}")
    private String apiServer; //API平台服务器


    public SettlementAccountOrientedTransferResponseV1 execute(SettlementAccountOrientedTransferRequestV1.SettlementAccountOrientedTransferRequestV1Biz bizContent) {

        SettlementAccountOrientedTransferRequestV1 request = new SettlementAccountOrientedTransferRequestV1();

        String serviceUrl = apiServer + ApiConstants.API_SETTLEMENT_ACCOUNT_ORIENTEDTRANSFER_V1;
        request.setServiceUrl(serviceUrl);
        request.setBizContent(bizContent);
        try {
            SettlementAccountOrientedTransferResponseV1 response = apiClientCopService.execute(request);

            return response;
        } catch (IcbcApiException e) {
            log.error(e.getMessage());
            throw new TransactionException(ResultCode.API_INVOKE_ERROR);
        }
    }
}
