package com.icbc.match.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.icbc.match.config.ApiConstants;
import com.icbc.match.entity.RequestBuilder;
import com.icbc.match.entry.Result;
import com.icbc.match.utils.SernoUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;

import static com.icbc.match.utils.Tools.getTime;

@Service
@Slf4j
public class SuppliersQryService {

    public  JSONObject suppliersQry(String merid,String debtorCode, String creditCode) {

        //发送请求到工银聚供应商信息查询接口
        //String merid = ApiConstants.API_YKGYJ_MERID;
        String fseqno = SernoUtil.getSerno();
        //  JSONObject param1 = new JSONObject();
        JSONObject data = new JSONObject();
        JSONObject response = new JSONObject();
        //   JSONObject param3 = new JSONObject();
        JSONObject reqdata = new JSONObject();
        reqdata.put("merid",merid);
        reqdata.put("timestamp",getTime());
        reqdata.put("fseqno",fseqno);
        reqdata.put("field1","");
        reqdata.put("field2","");
        reqdata.put("field3","");
        data.put("debtorCode",debtorCode);
        data.put("creditCode",creditCode);
        reqdata.put("data",data);

        log.info("reqdata{}" + reqdata);
        String result = null;
        try {
            result = new RequestBuilder(ApiConstants.API_YKGJY_URL,ApiConstants.API_YKGJY_AIPGW_APPID,ApiConstants.API_YKGJY_PRIKEY)
                    .apiname(ApiConstants.API_YKGYJ_COM_ICBC_GYJ_YSZK_SUPPLIERQRY)
                    .data(reqdata.toJSONString())
                    .build()
                    .query();
        } catch (IOException | IllegalAccessException e) {
            e.printStackTrace();
            response.put("return_code","8888");
            response.put("return_msg","请求工银聚授信查询接口SUPPLIERQRY失败");
            return response;
        }
        System.out.println(result);
        JSONObject jsresult = JSON.parseObject(result);
        return jsresult;
    }
}
