package com.icbc.match.api.service.icbc;

import cn.hutool.core.bean.BeanUtil;
import com.google.gson.Gson;
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
public class SettlementAccountBranchOpenV2Service {

    @Autowired
    private ApiClientService apiClientService;

    /**
     * 提供分行通过API接口开立个人II、III类结算账户功能
     * <p>
     * 必输
     * corp_no
     * trx_acc_date
     * trx_acc_time
     * corp_date
     * corp_serno，forever_flag，validity_period，sign_date，corp_medium_id，bind_medium，cert_type，cert_no，cust_name，mobile_no
     * gender，address，occupation，nationality，tax_declaration_flag，account_org_no
     *
     * @param settlementAccountBranchOpen
     * @return
     */
    public Map<String, Object> execute(SettlementAccountBranchOpen settlementAccountBranchOpen) {

        Map<String, Object> params = CamelCaseUtil.toLine(BeanUtil.beanToMap(settlementAccountBranchOpen));

        Map<String, Object> response = apiClientService.syncInvokeReqBodyJson(ApiConstants.API_SETTLEMENT_ACCOUNT_BRANCH_OPEN_V2, params);
        if (response != null) {
            log.debug("value:{}", response);
        }
        return response;
    }
}
