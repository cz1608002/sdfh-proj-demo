package com.icbc.match.api.service.icbc;

import cn.hutool.core.bean.BeanUtil;
import com.icbc.apip.invoker.Invoker;
import com.icbc.match.api.client.ApiClientService;
import com.icbc.match.config.ApiConstants;
import com.icbc.match.entity.SettlementAccountBranchRecharge;
import com.icbc.match.utils.CamelCaseUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@Slf4j
public class SettlementAccountBranchRechargeV1Service {

    @Autowired
    private ApiClientService apiClientService;

    /**
     * 结算账户充值接口
     *
     * @param settlementAccountBranchRecharge
     * @return
     */
    public Map<String, Object> execute(SettlementAccountBranchRecharge settlementAccountBranchRecharge) {

        Map<String, Object> params = CamelCaseUtil.toLine(BeanUtil.beanToMap(settlementAccountBranchRecharge));
        Map<String, Object> response = apiClientService.syncInvokeReqBodyJson(ApiConstants.API_SETTLEMENT_ACCOUNT_BRANCH_RECHARGE_V1,params);

        if (response != null) {
            log.debug("value:{}", response);
        }
        return response;
    }
}
