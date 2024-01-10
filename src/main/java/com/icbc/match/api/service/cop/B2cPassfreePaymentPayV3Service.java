package com.icbc.match.api.service.cop;

import com.icbc.api.IcbcApiException;
import com.icbc.api.request.B2cOrderqrySearchRequestV1;
import com.icbc.api.request.B2cPassfreePaymentPayRequestV3;
import com.icbc.api.response.B2cOrderqrySearchResponseV1;
import com.icbc.api.response.B2cPassfreePaymentPayResponseV3;
import com.icbc.match.api.client.ApiClientCopService;
import com.icbc.match.config.ApiConstants;
import com.icbc.match.entity.PassfreePaymentPayV2;
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
public class B2cPassfreePaymentPayV3Service {

    @Autowired
    private ApiClientCopService apiClientCopService;

    @Value("${icbc.api.url}")
    private String apiServer; //API平台服务器


    public B2cPassfreePaymentPayResponseV3 execute(B2cPassfreePaymentPayRequestV3.B2cPassfreePaymentPayRequestV3Biz bizContent) {

        B2cPassfreePaymentPayRequestV3 request = new B2cPassfreePaymentPayRequestV3();

        String serviceUrl = apiServer + ApiConstants.API_B2C_PASSFREE_PAYMENT_PAY_V3;

        request.setServiceUrl(serviceUrl);
        request.setBizContent(bizContent);
        try {
            B2cPassfreePaymentPayResponseV3 response = apiClientCopService.execute(request);

            return response;
        } catch (IcbcApiException e) {
            log.error("qrcode  generate error: ", e);
            throw new TransactionException(ResultCode.API_INVOKE_ERROR);
        }
    }
}
