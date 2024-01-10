package com.icbc.match.api.client;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.icbc.apip.config.SdkUtil;
import com.icbc.apip.exception.ConfigException;
import com.icbc.apip.exception.InvokerException;
import com.icbc.apip.invoker.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Map;
import java.util.Set;

/**
 * 对API SDK进行二次封装，将无界面接口和有界面接口封装为Serivice
 */
@Service
@Slf4j
public class ApiClientService {


    @Value("${icbc.api.configPath}")
    private String configPath; //ca证书


    @PostConstruct
    public void init() {
        log.info("init:" + configPath);
        System.setProperty(SdkUtil.SDK_CONFIG_FOLDER_PATH, configPath);
    }

    @PreDestroy
    public void destroy() {
        try {
            Invokers.shutdown();
        } catch (InvokerException e) {
            log.error(e.getMessage());
        } catch (ConfigException e) {
            log.error(e.getMessage());
        }
    }


    public Map<String, Object> syncInvoke(String uri, Invoker.HttpMethodType type, Map<String, Object> params) {

        Gson gson = new Gson();
        log.info("上送参数params：{}", gson.toJson(params));
        DefaultInvoker defaultInvoker = Invokers.getDefaultInvoker();
        defaultInvoker.setMethod(type);
        defaultInvoker.setUri(uri);
        /*defaultInvoker.setParameters(parameters);
         */
        /*JsonBuilder json = JsonBuilder.create();
        Set<String> keys = parameters.keySet();
        for (String key : keys) {
            try {
                json.add(key, parameters.get(key));
            } catch (InvokerException e) {
                log.error(e.getMessage());
            }
        }*/

        defaultInvoker.addParameter("biz_content", gson.toJson(params));
        Map<String, Object> res = null;
        try {
            res = defaultInvoker.syncInvoke();
        } catch (ConfigException e) {
            e.printStackTrace();
        } catch (InvokerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return res;
    }


    public Map<String, Object> syncInvokeReqJson(String uri, Invoker.HttpMethodType type, Map<String, Object> params) {

        Gson gson = new Gson();
        DefaultInvoker defaultInvoker = Invokers.getDefaultInvoker();
        defaultInvoker.setMethod(type);
        defaultInvoker.setUri(uri);
        /*defaultInvoker.setParameters(parameters);
         */
        defaultInvoker.addParameter("req_json", gson.toJson(params));

        Map<String, Object> res = null;
        try {
            res = defaultInvoker.syncInvoke();
        } catch (ConfigException e) {
            log.error(e.getMessage());
        } catch (InvokerException e) {
            log.error(e.getMessage());
        } catch (IOException e) {
            log.error(e.getMessage());
        }

        return res;

    }

    //行内调用使用
    public Map<String, Object> syncInvokeReqBodyJson(String url, Map<String, Object> params) {

      /*  Gson gson = new Gson();
        log.info("上送参数params：{}", gson.toJson(params));

        RequestBodyInvoker invoker = Invokers.getRequestBodyInvoker();
        invoker.setUri(url);
        try {
            String req_json = "[" + gson.toJson(params) + "]";
            invoker.setRequestBodyParam(req_json);
            res = invoker.syncInvoke();
        } catch (UnsupportedEncodingException e) {
            log.error(e.getMessage());
        } catch (InvokerException e) {
            log.error(e.getMessage());
        } catch (IOException e) {
            log.error(e.getMessage());
        } catch (ConfigException e) {
            log.error(e.getMessage());
        }
*/
        Map<String, Object> res = null;
        return res;
    }
}
