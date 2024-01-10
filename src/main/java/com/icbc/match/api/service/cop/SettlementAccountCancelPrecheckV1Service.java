package com.icbc.match.api.service.cop;

import com.icbc.api.IcbcApiException;
import com.icbc.api.request.SettlementAccountBindingQueryRequestV1;
import com.icbc.api.request.SettlementAccountCancelPreCheckRequestV1;
import com.icbc.api.response.SettlementAccountBindingQueryResponseV1;
import com.icbc.api.response.SettlementAccountCancelPreCheckResponseV1;
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
public class SettlementAccountCancelPrecheckV1Service {
    @Autowired
    private ApiClientCopService apiClientCopService;

    @Value("${icbc.api.url}")
    private String apiServer; //API平台服务器


    public SettlementAccountCancelPreCheckResponseV1 execute(SettlementAccountCancelPreCheckRequestV1.SettlementAccountCancelPreCheckRequestV1Biz bizContent) {

        SettlementAccountCancelPreCheckRequestV1 request = new SettlementAccountCancelPreCheckRequestV1();

        String serviceUrl = apiServer + ApiConstants.API_SETTLEMENT_ACCOUNT_CANCEL_PRECHECK_V1;
        request.setServiceUrl(serviceUrl);
        request.setBizContent(bizContent);
        try {
            SettlementAccountCancelPreCheckResponseV1 response = apiClientCopService.execute(request);

            return response;
        } catch (IcbcApiException e) {
            log.error(e.getMessage());
            throw new TransactionException(ResultCode.API_INVOKE_ERROR);
        }
    }
}
