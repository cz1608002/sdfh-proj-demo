package com.icbc.match.api.service.cop;

import com.icbc.api.IcbcApiException;
import com.icbc.api.request.SettlementAccountBindingRequestV1;
import com.icbc.api.request.SettlementAccountWithdrawRequestV1;
import com.icbc.api.response.SettlementAccountBindingResponseV1;
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
public class SettlementAccountV1BindingService {

    @Autowired
    private ApiClientCopService apiClientCopService;

    @Value("${icbc.api.url}")
    private String apiServer; //API平台服务器


    public SettlementAccountBindingResponseV1 execute(SettlementAccountBindingRequestV1.SettlementAccountBindingRequestV1Biz bizContent) {

        SettlementAccountBindingRequestV1 request = new SettlementAccountBindingRequestV1();

        String serviceUrl = apiServer + ApiConstants.API_SETTLEMENT_ACCOUNT_V1_BINDING;
        request.setServiceUrl(serviceUrl);
        request.setBizContent(bizContent);
        try {
            SettlementAccountBindingResponseV1 response = apiClientCopService.execute(request);

            return response;
        } catch (IcbcApiException e) {
            log.error("qrcode  generate error: ", e);
            throw new TransactionException(ResultCode.API_INVOKE_ERROR);
        }
    }
}
