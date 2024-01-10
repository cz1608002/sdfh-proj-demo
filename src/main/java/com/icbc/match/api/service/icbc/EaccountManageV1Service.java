package com.icbc.match.api.service.icbc;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.icbc.match.config.ApiConstants;
import com.icbc.match.entity.EaccountManageV1;
import com.icbc.match.utils.UFCSendUtil;

@Service
public class EaccountManageV1Service extends UFCSendUtil {

	
	public Map<String,Object> execute(EaccountManageV1 eaccountManageV1){
		return super.execute(eaccountManageV1, ApiConstants.UI_EACCOUNT_MANAGE_V1);
	}
}
