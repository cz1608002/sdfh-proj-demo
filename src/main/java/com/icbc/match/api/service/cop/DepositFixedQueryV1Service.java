package com.icbc.match.api.service.cop;

import com.icbc.api.IcbcApiException;
import com.icbc.api.request.DepositFixedQueryRequestV1;
import com.icbc.api.response.DepositFixedQueryResponseV1;
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
public class DepositFixedQueryV1Service {

    @Value("${icbc.api.url}")
    private String apiServer; //API平台服务器


    @Autowired
    private ApiClientCopService apiClientCopService;

    public DepositFixedQueryResponseV1 execute(DepositFixedQueryRequestV1.DepositFixedQueryRequestV1Biz bizContent) {

        String serviceUrl = apiServer + ApiConstants.API_DEPOSIT_FIXED_QUERY_V1;

        DepositFixedQueryRequestV1 request = new DepositFixedQueryRequestV1();
        request.setServiceUrl(serviceUrl);
        request.setBizContent(bizContent);


        try {
            DepositFixedQueryResponseV1 response = apiClientCopService.execute(request);

            return response;
        } catch (IcbcApiException e) {
            log.error("qrcode  generate error: ", e);
            throw new TransactionException(ResultCode.API_INVOKE_ERROR);
        }
    }
}
