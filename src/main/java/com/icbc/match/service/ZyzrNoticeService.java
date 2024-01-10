package com.icbc.match.service;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.icbc.match.config.ApiConstants;
import com.icbc.match.entity.KeyV;
import com.icbc.match.entity.ZyzrNotice;
import com.icbc.match.mapper.ZyzrNoticeMapper;
import com.icbc.match.utils.YkHttpPost;
import com.icbc.match.utils.SernoUtil;
import com.icbc.match.utils.Tools;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

import static com.icbc.match.utils.Tools.getTime;

@Service
@Slf4j
public class ZyzrNoticeService {

    @Autowired
    private ZyzrNoticeMapper zyzrNoticeMapper;


    @Autowired
    private SignCheckService signCheckService;

    public Map<String, Object> zyzrRecord(String apigwrspdata) {


        JSONObject jsapigwrspdata = JSON.parseObject(apigwrspdata);
        String merid = jsapigwrspdata.getString("merid");
        log.info("jsapigwrspdata{}" + merid);
        String fseqno = SernoUtil.getSerno();
        String field1 = jsapigwrspdata.getString("field1");
        String field2 = jsapigwrspdata.getString("field1");
        String field3 = jsapigwrspdata.getString("field3");
        JSONObject data = jsapigwrspdata.getJSONObject("data");
        String notifyno = data.getString("notifyno");
        String debtorCode = data.getString("debtorCode");
        String debtorName = data.getString("debtorName");
        String bankInCode = data.getString("bankInCode");
        String creditCode = data.getString("creditCode");
        String creditName = data.getString("creditCode");
        String customerId = data.getString("customerId");
        String bankCode = data.getString("bankCode");
        String bankName = data.getString("bankName");
        String ntType = data.getString("ntType");
        String recAccno = data.getString("recAccno");
        String recAccname = data.getString("recAccname");
        String recBankname = data.getString("recBankname");
        String ntDate = data.getString("ntDate");
        String count = data.getString("count");
        JSONArray accountList = data.getJSONArray("accountList");
        String accountCode = "";
        //int i = accountList.size();
        for (int i = 0; i < accountList.size(); i++) {
            accountCode = accountList.getJSONObject(i).getString("accountCode");
            String finCode = zyzrNoticeMapper.selectFinCodes(accountCode);
            ZyzrNotice notice = new ZyzrNotice();
            notice.setNtType(ntType);
            notice.setRecAccno(recAccno);
            notice.setRecAccname(recAccname);
            notice.setRecBankname(recBankname);
            notice.setNtDate(ntDate);
            notice.setNtcount(count);
            notice.setNotifyno(notifyno);
            notice.setCustomerId(customerId);
            notice.setFinCode(finCode);
           // notice.setAccountCode(accountCode);
           // notice.setAccCount(accountCode);
            int noticeNum = zyzrNoticeMapper.saveZyzrNoticeInfo(notice);
            JSONObject reqdata = new JSONObject();
            JSONObject param1 = new JSONObject();
            if (noticeNum < 0) {
                param1.put("errcode", "09");
                param1.put("errmsg", "");
                param1.put("lockstatus", "09");
                reqdata.put("data", param1);
                reqdata.put("merid", merid);
                reqdata.put("timestamp", getTime());
                reqdata.put("fseqno", fseqno);
                reqdata.put("field1", field1);
                reqdata.put("field2", field2);
                reqdata.put("field3", field3);
                reqdata.put("return_code", "9999");
                reqdata.put("return_msg", "插入数据库失败");

                return reqdata;
            }

            log.info("共插入数据条数：" + noticeNum);

        }


        //推送第三方

        String yknoticetUrl = ApiConstants.API_YK_HXQY_UPDATECONTRACTSIGN;
        Map<String, String> paramsMap = new HashMap<String, String>();
        String appid = merid;
        String key = ApiConstants.API_YK_KEY;
        String noce_str = new Random().nextLong() + "";
        String time_stamp = System.currentTimeMillis() / 1000 + "";

        List<KeyV> kvs = new ArrayList<KeyV>();
        kvs.add(new KeyV("appid", appid));
        kvs.add(new KeyV("noce_str", noce_str));
        kvs.add(new KeyV("time_stamp", time_stamp));

        String str = Tools.join(kvs);
        String ostr = str;
        str = str + "&key=" + key;
        String sign = "";
        sign = Tools.getMd5(str);

        String accountcode = accountCode;
        String finCode = zyzrNoticeMapper.selectFinCodes(accountcode);
        String gysdksqbh = finCode;
        String jgzh = recAccno;
        String yhcountry = "CN";
        String yhname = recBankname;
        String name = recAccname;
        String yhflag = "ICBC";
        String type = "0";
        JSONObject jsbody = new JSONObject();
        jsbody.put("gysdksqbh", gysdksqbh);
        jsbody.put("jgzh", jgzh);
        jsbody.put("yhcountry", yhcountry);
        jsbody.put("yhname", yhname);
        jsbody.put("name", name);
        jsbody.put("yhflag", yhflag);
        jsbody.put("type", type);
        String body = jsbody.toJSONString();
        System.out.println("body"+body);
        paramsMap.put("appid", appid);
        paramsMap.put("body", body);
        paramsMap.put("noce_str", noce_str);
        paramsMap.put("time_stamp", time_stamp);
        paramsMap.put("sign", sign);

        // 发送post请求并接收返回结果
        String resultData = YkHttpPost.httpPostWithForm(yknoticetUrl, paramsMap);
        System.out.println(resultData);
        JSONObject jsresult = JSON.parseObject(resultData);
        String code = jsresult.getString("code");
        String msg = jsresult.getString("msg");

        //       JSONObject checkresult = signCheckService.signCheck(finapplyinfo);


        JSONObject param1 = new JSONObject();
        JSONObject reqdata = new JSONObject();

        String errorcode = code;
        String lockstatus = "";
        String return_code = "";
        String errormsg = msg;
        String busscode = "";
        if ("10000".equals(code)) {
             errorcode = "01";
             lockstatus = "01";
             return_code = "0";
             busscode = notifyno;
             param1.put("busscode",busscode);

        }else{
            errorcode = code;
            lockstatus = "09";
            return_code = code;
        }
        param1.put("errcode", errorcode);
        param1.put("errmsg", errormsg);
        param1.put("lockstatus", lockstatus);

        reqdata.put("data", param1);
        reqdata.put("merid", merid);
        reqdata.put("timestamp", getTime());
        reqdata.put("fseqno", fseqno);
        reqdata.put("field1", field1);
        reqdata.put("field2", field2);
        reqdata.put("field3", field3);
        reqdata.put("return_code", return_code);
        reqdata.put("return_msg", msg);
       // reqdata.put("apigw_rspdata",param2);

        log.info(reqdata.toString());
        return reqdata;

    }
}




