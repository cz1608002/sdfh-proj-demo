package com.icbc.match.api.service.cop;

import com.icbc.api.IcbcApiException;
import com.icbc.api.request.DepositFixedProdqueryRequestV1;
import com.icbc.api.request.DepositFixedTotalqueryRequestV1;
import com.icbc.api.response.DepositFixedProdqueryResponseV1;
import com.icbc.api.response.DepositFixedTotalqueryResponseV1;
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
public class DepositFixedProdqueryV1Service {

    @Value("${icbc.api.url}")
    private String apiServer; //API平台服务器


    @Autowired
    private ApiClientCopService apiClientCopService;

    public DepositFixedProdqueryResponseV1 execute(DepositFixedProdqueryRequestV1.DepositFixedProdqueryRequestV1Biz bizContent) {

        String serviceUrl = apiServer + ApiConstants.API_DEPOSIT_FIXED_PRODQUERY_V1;

        DepositFixedProdqueryRequestV1 request = new DepositFixedProdqueryRequestV1();
        request.setServiceUrl(serviceUrl);
        request.setBizContent(bizContent);


        try {
            DepositFixedProdqueryResponseV1 response = apiClientCopService.execute(request);

            return response;
        } catch (IcbcApiException e) {
            log.error("qrcode  generate error: ", e);
            throw new TransactionException(ResultCode.API_INVOKE_ERROR);
        }
    }
}
