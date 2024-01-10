package com.icbc.match.api.service.cop;

import com.icbc.api.IcbcApiException;
import com.icbc.api.request.SettlementAccountOpenRealtimeimgUploadRequestV1;
import com.icbc.api.request.SettlementAccountStatusQueryRequestV1;
import com.icbc.api.response.SettlementAccountOpenRealtimeimgUploadResponseV1;
import com.icbc.api.response.SettlementAccountStatusQueryResponseV1;
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
public class SettlementAccountStatusQueryV1Service {

    @Autowired
    private ApiClientCopService apiClientCopService;

    @Value("${icbc.api.url}")
    private String apiServer; //API平台服务器


    public SettlementAccountStatusQueryResponseV1 execute(SettlementAccountStatusQueryRequestV1.SettlementAccountStatusQueryRequestV1Biz bizContent) {

        SettlementAccountStatusQueryRequestV1 request = new SettlementAccountStatusQueryRequestV1();

        String serviceUrl = apiServer + ApiConstants.API_SETTLEMENT_ACCOUNT_STATUS_QUERY_V1;
        request.setServiceUrl(serviceUrl);
        request.setBizContent(bizContent);
        try {
            SettlementAccountStatusQueryResponseV1 response = apiClientCopService.execute(request);

            return response;
        } catch (IcbcApiException e) {
            log.error("qrcode  generate error: ", e);
            throw new TransactionException(ResultCode.API_INVOKE_ERROR);
        }
    }
}
