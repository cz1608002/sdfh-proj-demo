package com.icbc.match.api.service.cop;

import com.icbc.api.IcbcApiException;
import com.icbc.api.request.DepositFixedAccountlockRequestV1;
import com.icbc.api.request.DepositFixedProdqueryRequestV1;
import com.icbc.api.response.DepositFixedAccountlockResponseV1;
import com.icbc.api.response.DepositFixedProdqueryResponseV1;
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
public class DepositFixedAccountlockV1Service {

    @Value("${icbc.api.url}")
    private String apiServer; //API平台服务器


    @Autowired
    private ApiClientCopService apiClientCopService;

    public DepositFixedAccountlockResponseV1 execute(DepositFixedAccountlockRequestV1.DepositFixedAccountlockRequestV1Biz bizContent) {

        String serviceUrl = apiServer + ApiConstants.API_DEPOSIT_FIXED_ACCOUNTLOCK_V1;

        DepositFixedAccountlockRequestV1 request = new DepositFixedAccountlockRequestV1();
        request.setServiceUrl(serviceUrl);
        request.setBizContent(bizContent);


        try {
            DepositFixedAccountlockResponseV1 response = apiClientCopService.execute(request);

            return response;
        } catch (IcbcApiException e) {
            log.error("qrcode  generate error: ", e);
            throw new TransactionException(ResultCode.API_INVOKE_ERROR);
        }
    }
}
