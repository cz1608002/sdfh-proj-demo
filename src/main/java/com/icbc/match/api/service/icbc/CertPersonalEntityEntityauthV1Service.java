package com.icbc.match.api.service.icbc;

import cn.hutool.core.bean.BeanUtil;
import com.google.gson.Gson;
import com.icbc.apip.invoker.Invoker;
import com.icbc.match.api.client.ApiClientService;
import com.icbc.match.config.ApiConstants;
import com.icbc.match.entity.CertPersonalEntityEntityauth;
import com.icbc.match.utils.CamelCaseUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@Slf4j
public class CertPersonalEntityEntityauthV1Service {

    @Autowired
    private ApiClientService apiClientService;


    /**
     * 身份实体认证服务
     *
     * @param certPersonalEntityEntityauth,参数：姓名，身份证，图片 50k  base64无换行符
     * @return
     */
    public Map<String, Object> execute(CertPersonalEntityEntityauth certPersonalEntityEntityauth) {
        Gson gson = new Gson();
        Map<String, Object> params = CamelCaseUtil.notToLine((BeanUtil.beanToMap(certPersonalEntityEntityauth)));
        log.info(gson.toJson(params));
        Map<String, Object> response = apiClientService.syncInvoke(ApiConstants.API_CERT_PERSONAL_ENTITY_ENTITYAUTH_V1, Invoker.HttpMethodType.POST, params);
        if (response != null) {
            log.debug("value:{}", response);
        }
        return response;
    }
}
