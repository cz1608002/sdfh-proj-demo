package com.icbc.match.api.service.icbc;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.icbc.match.config.ApiConstants;
import com.icbc.match.entity.CertPersonalEntityEntityauth;
import com.icbc.match.utils.UFCSendUtil;

@Service
public class TestToolService  extends UFCSendUtil{

	public Map<String ,Object> execute(CertPersonalEntityEntityauth certPersonalEntityEntityauth){
		return super.execute(certPersonalEntityEntityauth, ApiConstants.API_CERT_PERSONAL_ENTITY_ENTITYAUTH_V1);
	}
}
