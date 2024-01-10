package com.icbc.match.api.service.icbc;

import cn.hutool.core.bean.BeanUtil;
import com.icbc.apip.invoker.Invoker;
import com.icbc.match.api.client.ApiClientService;
import com.icbc.match.config.ApiConstants;
import com.icbc.match.entity.SettlementAccountBranchScodeVerify;
import com.icbc.match.utils.CamelCaseUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@Slf4j
public class SettlementAccountBranchScodeVerifyV1Service {

    @Autowired
    private ApiClientService apiClientService;


    public Map<String, Object>
    execute(SettlementAccountBranchScodeVerify settlementAccountBranchScodeVerify) {

        Map<String, Object> params = CamelCaseUtil.toLine(BeanUtil.beanToMap(settlementAccountBranchScodeVerify));
        Map<String, Object> response = apiClientService.syncInvokeReqBodyJson(ApiConstants.API_SETTLEMENT_ACCOUNT_BRANCH_SCODE_VERIFY_V1, params);
        if (response != null) {
            log.debug("value:{}", response);
        }
        return response;
    }
}
