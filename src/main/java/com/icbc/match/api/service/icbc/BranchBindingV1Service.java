package com.icbc.match.api.service.icbc;


import cn.hutool.core.bean.BeanUtil;
import com.icbc.apip.invoker.Invoker;
import com.icbc.match.api.client.ApiClientService;
import com.icbc.match.config.ApiConstants;
import com.icbc.match.entity.BranchBinding;
import com.icbc.match.utils.CamelCaseUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class BranchBindingV1Service {


    @Autowired
    private ApiClientService apiClientService;


    /**
     * 结算账户绑卡维护
     *
     * @param branchBinding
     * @return
     */
    public Map<String, Object> branchBinding(BranchBinding branchBinding) {

        Map<String, Object> params = CamelCaseUtil.notToLine(BeanUtil.beanToMap(branchBinding));
        Map<String, Object> response = apiClientService.syncInvokeReqBodyJson(ApiConstants.API_SETTLEMENT_ACCOUNT_BRANCH_BINDING_V1,params);
        //Map<String, Object> response = apiClientService.syncInvoke(ApiConstants.API_SETTLEMENT_ACCOUNT_BRANCH_BINDING_V1, Invoker.HttpMethodType.POST, params);
        if (response != null) {
            log.debug("value:{}", response);
        }
        return response;
    }
}
