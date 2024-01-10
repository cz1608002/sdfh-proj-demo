package com.icbc.match.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.icbc.match.api.client.ApiClientCopService;
import com.icbc.match.config.ApiConstants;
import com.icbc.match.entity.Body;
import com.icbc.match.entity.CreditQryList;
import com.icbc.match.entity.RequestBuilder;
import com.icbc.match.entry.Result;
import com.icbc.match.entry.YkgyjResult;
import com.icbc.match.mapper.ZyzrNoticeMapper;
import com.icbc.match.utils.SernoUtil;
import com.icbc.match.utils.Tools;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import static com.icbc.match.utils.Tools.getTime;

@Service
@Slf4j
public class PledgeLockService {
    @Autowired
    private ZyzrNoticeMapper zyzrNoticeMapper;

    public JSONObject pledgeLock(Body body) {

//        JSONObject jsbody = JSON.parseObject(body);
//        String fincode = jsbody.getString("gysdksqbh");
//        String lockstatus = jsbody.getString("type");
        String fincode = body.getGysdksqbh();
        String lockstatus = body.getType();

        System.out.println("lockstatus"+ lockstatus);

        //根据fincode查询notifyno

        String timestamp = Tools.getTime();
        String fseqno = SernoUtil.getSerno();
        ArrayList<CreditQryList> CreditQryList = zyzrNoticeMapper.selectNotifyNo(fincode);
        String merid = CreditQryList.get(0).getMerid() ;
        String notifyno = CreditQryList.get(0).getNotifyno();
        String debtorcode = CreditQryList.get(0).getDebtorCode();
        JSONObject reqdata = new JSONObject();
        JSONObject data = new JSONObject();
        if ("1".equals(lockstatus)){
            lockstatus = "03";
            data.put("busscode",notifyno);
        }else if ("0".equals(lockstatus)){
            lockstatus = "01";
            data.put("busscode",notifyno);
        }else {
            lockstatus = "09";
        }

        reqdata.put("merid",merid);
        reqdata.put("timestamp",timestamp);
        reqdata.put("fseqno",fseqno);
        data.put("notifyno",notifyno);
        data.put("lockstatus",lockstatus);
        data.put("debtorCode",debtorcode);
        data.put("busscode",fseqno);
        reqdata.put("data",data);

        System.out.println("请求信息：" + reqdata);
        String presult = null;
        try {
            presult = new RequestBuilder(ApiConstants.API_YKGJY_URL,ApiConstants.API_YKGJY_AIPGW_APPID,ApiConstants.API_YKGJY_PRIKEY)
                    .apiname(ApiConstants.API_YKGYJ_COM_ICBC_GYJ_YSZK_PLEDGERESULT)
                    .data(reqdata.toJSONString())
                    .build()
                    .query();
        } catch (IllegalAccessException  | IOException e) {
            e.printStackTrace();
            return YkgyjResult.failed("9999","请求工银聚账号锁定失败");
        }
        System.out.println(presult);

        JSONObject obj = JSON.parseObject(presult);
        String qreturn_code = obj.getString("return_code");
        if ("0".equals(qreturn_code)) {
            String code = "10000";
            String msg = "成功";
            return YkgyjResult.success(code,msg);
        }else{
            String msg = obj.getString("return_msg");
            String code = "9999";
            return YkgyjResult.success(code,msg);
        }
    }
}
