package com.icbc.match.api.service.icbc;

import cn.hutool.core.bean.BeanUtil;
import com.icbc.apip.invoker.Invoker;
import com.icbc.match.api.client.ApiClientService;
import com.icbc.match.config.ApiConstants;
import com.icbc.match.entity.SettlementAccountBranchQueryApply;
import com.icbc.match.utils.CamelCaseUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@Slf4j
public class SettlementAccountBranchQueryApplyV1Service {


    @Autowired
    private ApiClientService apiClientService;

    @Value("${icbc.api.url}")
    private String apiServer; //API平台服务器


    public Map<String, Object> settlementAccountBranchQueryApply(SettlementAccountBranchQueryApply settlementAccountBranchQueryApply) {

        Map<String, Object> params = CamelCaseUtil.toLine(BeanUtil.beanToMap(settlementAccountBranchQueryApply));

        Map<String, Object> response = apiClientService.syncInvokeReqBodyJson(ApiConstants.API_SETTLEMENT_ACCOUNT_BRANCH_QUERY_APPLY_V1,params);
        if (response != null) {
            log.debug("value:{}", response);
        }
        return response;
    }
}
