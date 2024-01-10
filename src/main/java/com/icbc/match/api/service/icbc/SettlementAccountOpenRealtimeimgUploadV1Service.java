package com.icbc.match.api.service.icbc;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.icbc.match.api.client.ApiClientService;
import com.icbc.match.config.ApiConstants;
import com.icbc.match.entity.SettlementAccountBranchRealtimeimgUpload;
import com.icbc.match.utils.CamelCaseUtil;

import cn.hutool.core.bean.BeanUtil;
import lombok.extern.slf4j.Slf4j;

@Service("icbc")
@Slf4j
public class SettlementAccountOpenRealtimeimgUploadV1Service {
	@Autowired
	private ApiClientService apiClientService;

	public Map<String, Object> execute(
			SettlementAccountBranchRealtimeimgUpload settlementAccountBranchRealtimeimgUpload) {

		Map<String, Object> params = CamelCaseUtil.toLine(BeanUtil.beanToMap(settlementAccountBranchRealtimeimgUpload));
		Map<String, Object> response = apiClientService
				.syncInvokeReqBodyJson(ApiConstants.API_SETTLEMENT_ACCOUNT_BRANCH_REALTIMEIMG_UPLOAD_V1, params);
		if (response != null) {
			log.debug("value:{}", response);
		}
		return response;
	}
}
