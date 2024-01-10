package com.icbc.match.api.service.cop;

import com.icbc.api.IcbcApiException;
import com.icbc.api.request.DepositFixedRedeemRequestV1;
import com.icbc.api.response.DepositFixedRedeemResponseV1;
import com.icbc.match.api.client.ApiClientCopService;
import com.icbc.match.config.ApiConstants;
import com.icbc.match.entry.ResultCode;
import com.icbc.match.exception.TransactionException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * 二维码API接口服务
 */
@Service
@Slf4j
public class DepositFixedRedeemV1Service {

    @Value("${icbc.api.url}")
    private String apiServer; //API平台服务器


    @Autowired
    private ApiClientCopService apiClientCopService;

    public DepositFixedRedeemResponseV1 execute(DepositFixedRedeemRequestV1.DepositFixedRedeemRequestV1Biz bizContent) {

        String serviceUrl = apiServer + ApiConstants.API_DEPOSIT_FIXED_REDEEM_V1;

        DepositFixedRedeemRequestV1 request = new DepositFixedRedeemRequestV1();
        request.setServiceUrl(serviceUrl);
        request.setBizContent(bizContent);


        try {
            DepositFixedRedeemResponseV1 response = apiClientCopService.execute(request);

            return response;
        } catch (IcbcApiException e) {
            log.error("qrcode  generate error: ", e);
            throw new TransactionException(ResultCode.API_INVOKE_ERROR);
        }
    }
}
