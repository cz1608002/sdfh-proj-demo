package com.icbc.match.api.service.cop;

import com.icbc.api.IcbcApiException;
import com.icbc.api.request.SettlementAccountRechargeRequestV1;
import com.icbc.api.request.SettlementAccountSCodeVerifyRequestV1;
import com.icbc.api.response.SettlementAccountRechargeResponseV1;
import com.icbc.api.response.SettlementAccountSCodeVerifyResponseV1;
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
public class SettlementAccountV1RechargeService {

    @Autowired
    private ApiClientCopService apiClientCopService;

    @Value("${icbc.api.url}")
    private String apiServer; //API平台服务器


    public SettlementAccountRechargeResponseV1 execute(SettlementAccountRechargeRequestV1.SettlementAccountRechargeRequestV1Biz bizContent) {

        SettlementAccountRechargeRequestV1 request = new SettlementAccountRechargeRequestV1();

        String serviceUrl = apiServer + ApiConstants.API_SETTLEMENT_ACCOUNT_V1_RECHARGE;
        request.setServiceUrl(serviceUrl);
        request.setBizContent(bizContent);
        try {
            SettlementAccountRechargeResponseV1 response = apiClientCopService.execute(request);

            return response;
        } catch (IcbcApiException e) {
            log.error("qrcode  generate error: ", e);
            throw new TransactionException(ResultCode.API_INVOKE_ERROR);
        }
    }
}
