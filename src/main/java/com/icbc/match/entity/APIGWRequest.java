package com.icbc.match.entity;

import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.icbc.api.core.ApiClient;
import com.icbc.api.core.ApiRequest;
import com.icbc.api.core.ApiResponse;




/**
 *
 *
 * @date 2019年9月4日
 * @author zjfh-chent
 */
public class APIGWRequest{


    private APIEntity ae;

    /**
     * @param ae
     */
    APIGWRequest(APIEntity ae) {
        this.ae = ae;
    }


    public String query() throws IllegalArgumentException, IllegalAccessException{

        if(ae.getApiurl() == null || ae.getApiurl().isEmpty()){
            throw new IllegalArgumentException("apiurl不能为空");
        }

        if(ae.getApiname() == null || ae.getApigw_appid().isEmpty()){
            throw new IllegalArgumentException("apiname不能为空");
        }

        if(ae.getApigw_appid() == null || ae.getApigw_appid().isEmpty()){
            throw new IllegalArgumentException("apigw_appid不能为空");
        }

        if(ae.getReqdata() == null || ae.getReqdata().isEmpty()){
            throw new IllegalArgumentException("请求参数不能为空");
        }


        String reqdata = ae.getReqdata();

        //签名
        ApiClient ac = new ApiClient(ae.getPriKey());

        //请求
        ApiRequest req = new ApiRequest(ae.getApiurl(),ae.getApiname(),ae.getApigw_appid());
        req.setRequestField(ae.getField(), reqdata);


        //其他请求数据遍历
        for(Property p: ae.getProperties()){
            req.setRequestField(p.getKey(), p.getValue().toString());
        }

        //处理响应
        String result = "";
        try {
            ApiResponse ar = ac.execute(req);

            System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<"+ar.getSignBlock());

            if(ar.isCheckValid() && ar.getLong("ICBC_API_RETCODE")==0){
                Map<Object, Object> res = ar.getMap("response");
                return JSON.toJSONString(res);
            }else{
                result = ar.getString("ICBC_API_RETMSG");
            }
        } catch (Exception e) {
            // 异常处理
            result = "error"+e;
            e.printStackTrace();
        }
        return result;

    }
}
