package com.icbc.match.api.service.cop;

import com.icbc.api.IcbcApiException;
import com.icbc.api.request.B2cPassfreePaymentMessagesendRequestV1;
import com.icbc.api.response.B2cPassfreePaymentMessagesendResponseV1;
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
public class B2cPassfreePaymentMessagesendV1Service {
    @Autowired
    private ApiClientCopService apiClientCopService;

    @Value("${icbc.api.url}")
    private String apiServer; //API平台服务器


    public B2cPassfreePaymentMessagesendResponseV1 execute(B2cPassfreePaymentMessagesendRequestV1.B2cPassfreePaymentMsgSendRequestV1Biz bizContent) {

        B2cPassfreePaymentMessagesendRequestV1 request = new B2cPassfreePaymentMessagesendRequestV1();

        String serviceUrl = apiServer + ApiConstants.API_B2C_PASSFREE_PAYMENT_MESSAGESEND_V1;

        request.setServiceUrl(serviceUrl);
        request.setBizContent(bizContent);
        try {
            B2cPassfreePaymentMessagesendResponseV1 response = apiClientCopService.execute(request);

            return response;
        } catch (IcbcApiException e) {
            log.error("qrcode  generate error: ", e);
            throw new TransactionException(ResultCode.API_INVOKE_ERROR);
        }
    }

}
