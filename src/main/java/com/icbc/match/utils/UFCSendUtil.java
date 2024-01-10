package com.icbc.match.utils;

import java.util.Map;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.hutool.core.bean.BeanUtil;

import com.google.gson.Gson;
import com.icbc.apip.invoker.Invoker;
import com.icbc.match.api.client.ApiClientService;
import com.icbc.match.config.ApiConstants;

//@Service
@Slf4j
public  class UFCSendUtil {
	
	@Autowired
	private ApiClientService apiClientService;
	
	
	public <T> Map<String,Object> execute(T t,String url){
		    Gson gson = new Gson();
	        Map<String, Object> params = CamelCaseUtil.notToLine((BeanUtil.beanToMap(t)));
	        log.info(gson.toJson(params));
	        Map<String, Object> response = apiClientService.syncInvoke(url, Invoker.HttpMethodType.POST, params);
	        if (response != null) {
	            log.debug("value:{}", response);
	        }
	        return response;
//	        System.err.println(params.toString());
//	        return params;
		
	}
	
	

}
