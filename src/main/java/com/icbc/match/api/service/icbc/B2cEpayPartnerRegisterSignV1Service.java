package com.icbc.match.api.service.icbc;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.icbc.match.config.ApiConstants;
import com.icbc.match.entity.B2cEpayPartnerRegisterSignV1;
import com.icbc.match.utils.UFCSendUtil;

@Service
public class B2cEpayPartnerRegisterSignV1Service extends UFCSendUtil {

	
	public Map<String,Object> execute(B2cEpayPartnerRegisterSignV1 b2cEpayPartnerRegisterSignV1){
		return super.execute(b2cEpayPartnerRegisterSignV1, ApiConstants.UI_B2C_EPAY_PARTNER_REGISTER_SIGN_V1);
	}
}
