package com.icbc.match.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.icbc.match.config.ApiConstants;
import com.icbc.match.entity.Body;
import com.icbc.match.entity.FinApplyInfo;
import com.icbc.match.entity.KeyV;
import com.icbc.match.entry.Result;
import com.icbc.match.entry.YkgyjResult;
import com.icbc.match.utils.MD5Util;
import com.icbc.match.utils.Tools;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Slf4j
public class SignCheckService {

    public JSONObject signCheck(FinApplyInfo finapplyinfo){

        String appid = finapplyinfo.getAppid();
        String noce_str = finapplyinfo.getNoce_str();
        String time_stamp = finapplyinfo.getTime_stamp();
        String sign =  finapplyinfo.getSign();
        Body supply_info = finapplyinfo.getBody();
//        String fincode = supply_info.getGysdksqbh();
  //      String signEcrypt = MD5Util.string2MD5(appid + nonce_str + time_stamp);
        String key = ApiConstants.API_YK_KEY;
        String skey = "&key=" + key;
        String signEcrypt = Tools.getMd5("appid="+ appid +"&noce_str="+ noce_str + "&time_stamp=" + time_stamp + skey);

        log.info("appid:" + appid);
        log.info("nonce_str:" + noce_str);
        log.info("time_stamp:" + time_stamp);
        log.info("key:" + key);
        log.info("skey:" + skey);
        //       log.info("fincode" + fincode);
        log.info("sign:" + sign);
        log.info("signEcrypt:" + signEcrypt);

        long timestamp = 0;
        timestamp = Long.parseLong(time_stamp);
        log.info("timestamp:" + timestamp);
        log.info(String.valueOf(System.currentTimeMillis()/1000));
        if (Math.abs(timestamp - System.currentTimeMillis()/1000) > 180){
            log.info("time_stamp无效....");
            return YkgyjResult.failed("9999","time_stamp无效........");

        }
        if (!(sign.equalsIgnoreCase(signEcrypt))){
            log.info("sign签名校验失败...........");
            return YkgyjResult.failed("9999","sign签名校验失败...........");
        }

        return YkgyjResult.success("0000","sign校验成功");

    }

//    public Map<String, String> signResult(JSONObject result){
//
//
//
//        String appid = ApiConstants.API_YKGYJ_MERID;
//        String key = ApiConstants.API_YK_KEY;
//        String noce_str = new Random().nextLong() + "";
//        String time_stamp = System.currentTimeMillis() / 1000 + "";
//        Map<String, String> paramsMap = new HashMap<String, String>();
////        String fincode = supply_info.getGysdksqbh();
//        List<KeyV> kvs = new ArrayList<KeyV>();
//        kvs.add(new KeyV("appid", appid));
//        kvs.add(new KeyV("noce_str", noce_str));
//        kvs.add(new KeyV("time_stamp", time_stamp));
//
//        String str = Tools.join(kvs);
//        String ostr = str;
//        str = str + "&key=" + key;
//        String sign = "";
//        sign = Tools.getMd5(str);
//
//        String sresult = JSON.toJSONString(result);
//        paramsMap.put("key", key);
//        paramsMap.put("appid", appid);
//        paramsMap.put("body", sresult);
//        paramsMap.put("noce_str", noce_str);
//        paramsMap.put("time_stamp", time_stamp);
//        paramsMap.put("sign", sign);
//        log.info("appid:" + appid);
//        //       log.info("fincode" + fincode);
//
//
//        return paramsMap;
//
//
//    }

}
