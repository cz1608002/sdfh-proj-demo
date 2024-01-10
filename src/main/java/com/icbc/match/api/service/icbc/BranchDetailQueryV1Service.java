package com.icbc.match.api.service.icbc;


import cn.hutool.core.bean.BeanUtil;
import com.icbc.apip.invoker.Invoker;
import com.icbc.match.api.client.ApiClientService;
import com.icbc.match.config.ApiConstants;
import com.icbc.match.entity.BranchDetailQuery;
import com.icbc.match.utils.CamelCaseUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class BranchDetailQueryV1Service {


    @Autowired
    private ApiClientService apiClientService;


    /**
     * 结算账户交易明细查询接口
     *
     * @param branchDetailquery
     * @return
     */
        public Map<String, Object>  branchDetailquery(BranchDetailQuery branchDetailquery) {

            Map<String, Object> params = CamelCaseUtil.notToLine(BeanUtil.beanToMap(branchDetailquery));

            Map<String, Object> response = apiClientService.syncInvokeReqBodyJson(ApiConstants.API_SETTLEMENT_ACCOUNT_BRANCH_DETAIL_QUERY_V1,params);

            if (response != null) {
                log.debug("value:{}", response);
            }
            return response;
    }
}
