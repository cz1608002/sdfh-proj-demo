package com.icbc.match.api.service.cop;

import com.icbc.api.IcbcApiException;
import com.icbc.api.request.SettlementAccountBalanceQueryRequestV1;
import com.icbc.api.request.SettlementAccountDetailQueryRequestV1;
import com.icbc.api.response.SettlementAccountBalanceQueryResponseV1;
import com.icbc.api.response.SettlementAccountDetailQueryResponseV1;
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
public class SettlementAccountBalanceV1QueryService {

    @Autowired
    private ApiClientCopService apiClientCopService;

    @Value("${icbc.api.url}")
    private String apiServer; //API平台服务器


    public SettlementAccountBalanceQueryResponseV1 execute(SettlementAccountBalanceQueryRequestV1.SettlementAccountBalanceQueryRequestV1Biz bizContent) {

        SettlementAccountBalanceQueryRequestV1 request = new SettlementAccountBalanceQueryRequestV1();

        String serviceUrl = apiServer + ApiConstants.API_SETTLEMENT_ACCOUNT_BALANCE_V1_QUERY;
        request.setServiceUrl(serviceUrl);
        request.setBizContent(bizContent);
        try {
            SettlementAccountBalanceQueryResponseV1 response = apiClientCopService.execute(request);

            return response;
        } catch (IcbcApiException e) {
            log.error("qrcode  generate error: ", e);
            throw new TransactionException(ResultCode.API_INVOKE_ERROR);
        }
    }
}
