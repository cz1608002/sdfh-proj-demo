package com.icbc.match.api.service.cop;

import cn.hutool.core.bean.BeanUtil;
import com.icbc.api.IcbcApiException;
import com.icbc.api.request.CertPersonalProofProofcheckRequestV1;
import com.icbc.api.request.SettlementAccountOpenRequestV2;
import com.icbc.api.response.CertPersonalProofProofcheckResponseV1;
import com.icbc.api.response.SettlementAccountOpenResponseV2;
import com.icbc.match.api.client.ApiClientCopService;
import com.icbc.match.api.client.ApiClientService;
import com.icbc.match.config.ApiConstants;
import com.icbc.match.entity.SettlementAccountBranchOpen;
import com.icbc.match.entry.ResultCode;
import com.icbc.match.exception.TransactionException;
import com.icbc.match.utils.CamelCaseUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@Slf4j
public class SettlementAccountOpenV2Service {

    @Autowired
    private ApiClientCopService apiClientCopService;

    @Value("${icbc.api.url}")
    private String apiServer; //API平台服务器


    public SettlementAccountOpenResponseV2 execute(SettlementAccountOpenRequestV2.SettlementAccountOpenRequestV2Biz bizContent) {

        SettlementAccountOpenRequestV2 request = new SettlementAccountOpenRequestV2();

        String serviceUrl = apiServer + ApiConstants.API_SETTLEMENT_ACCOUNT_OPEN_V2;

        request.setServiceUrl(serviceUrl);
        request.setBizContent(bizContent);
        try {
            SettlementAccountOpenResponseV2 response = apiClientCopService.execute(request);

            return response;
        } catch (IcbcApiException e) {
            log.error("qrcode  generate error: ", e);
            throw new TransactionException(ResultCode.API_INVOKE_ERROR);
        }
    }
}
