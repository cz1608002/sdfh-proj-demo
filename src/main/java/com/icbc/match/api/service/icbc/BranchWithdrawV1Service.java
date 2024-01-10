package com.icbc.match.api.service.icbc;


import cn.hutool.core.bean.BeanUtil;
import com.icbc.apip.invoker.Invoker;
import com.icbc.match.api.client.ApiClientService;
import com.icbc.match.config.ApiConstants;
import com.icbc.match.entity.BranchWithdraw;
import com.icbc.match.utils.CamelCaseUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class BranchWithdrawV1Service {


    @Autowired
    private ApiClientService apiClientService;


    /**
     * 结算账户提现
     *
     * @param branchWithdraw
     * @return
     */
    public Map<String, Object> branchWithdraw(BranchWithdraw branchWithdraw) {

        Map<String, Object> params = CamelCaseUtil.notToLine(BeanUtil.beanToMap(branchWithdraw));

       // Map<String, Object> response = apiClientService.syncInvoke(ApiConstants.API_SETTLEMENT_ACCOUNT_BRANCH_WITHDRAW_V1, Invoker.HttpMethodType.POST, params);
        Map<String, Object> response = apiClientService.syncInvokeReqBodyJson(ApiConstants.API_SETTLEMENT_ACCOUNT_BRANCH_WITHDRAW_V1,params);
        if (response != null) {
            log.debug("value:{}", response);
        }
        return response;
    }
}
