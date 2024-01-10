package com.icbc.match.api.service.cop;

import com.icbc.api.IcbcApiException;
import com.icbc.api.request.B2cPassfreePaymentSignRequestV1;
import com.icbc.api.request.B2cPassfreePaymentTerminateRequestV1;
import com.icbc.api.response.B2cPassfreePaymentSignResponseV1;
import com.icbc.api.response.B2cPassfreePaymentTerminateResponseV1;
import com.icbc.match.api.client.ApiClientCopService;
import com.icbc.match.config.ApiConstants;
import com.icbc.match.entity.PassfreePaymentSignV1;
import com.icbc.match.entry.ResultCode;
import com.icbc.match.exception.TransactionException;
import com.icbc.match.utils.UFCSendUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@Slf4j
public class B2cPassfreePaymentSignV1Service {

    @Autowired
    private ApiClientCopService apiClientCopService;

    @Value("${icbc.api.url}")
    private String apiServer; //API平台服务器


    public B2cPassfreePaymentSignResponseV1 execute(B2cPassfreePaymentSignRequestV1.B2cPassfreePaymentSignRequestV1Biz bizContent) {

        B2cPassfreePaymentSignRequestV1 request = new B2cPassfreePaymentSignRequestV1();

        String serviceUrl = apiServer + ApiConstants.API_B2C_PASSFREE_PAYMENT_SIGN_V1;

        request.setServiceUrl(serviceUrl);
        request.setBizContent(bizContent);
        try {
            B2cPassfreePaymentSignResponseV1 response = apiClientCopService.execute(request);

            return response;
        } catch (IcbcApiException e) {
            log.error("qrcode  generate error: ", e);
            throw new TransactionException(ResultCode.API_INVOKE_ERROR);
        }
    }
}
