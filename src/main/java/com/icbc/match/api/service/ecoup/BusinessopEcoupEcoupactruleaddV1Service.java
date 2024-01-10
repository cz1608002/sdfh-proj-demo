package com.icbc.match.api.service.ecoup;

import cn.hutool.core.bean.BeanUtil;
import com.icbc.match.api.client.ApiClientService;
import com.icbc.match.config.ApiConstants;
import com.icbc.match.entity.SettlementAccountBranchOpen;
import com.icbc.match.utils.CamelCaseUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@Slf4j
public class BusinessopEcoupEcoupactruleaddV1Service {

    @Autowired
    private ApiClientService apiClientService;

    /**
     *
     */
    public Map<String, Object> execute(Map<String, Object> params) {


        Map<String, Object> response = apiClientService.syncInvokeReqBodyJson(ApiConstants.API_BUSINESSOP_ECOUP_ECOUPACTRULEADD_V1, params);
        if (response != null) {
            log.debug("value:{}", response);
        }
        return response;
    }
}
