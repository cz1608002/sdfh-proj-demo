package com.icbc.match.controller;



import com.alibaba.fastjson.JSONObject;
import com.icbc.match.entity.Body;
import com.icbc.match.entity.FinApply;
import com.icbc.match.entity.FinApplyInfo;
import com.icbc.match.entry.Result;
import com.icbc.match.entry.YkgyjResult;
import com.icbc.match.service.FinApplyService;
import com.icbc.match.service.FinApplyService2;
import com.icbc.match.service.SignCheckService;
import com.icbc.match.service.ZyzrNoticeService;
import com.icbc.match.utils.MD5Util;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@Slf4j
@RestController
public class FinApplyController {

    private String serverPrefix;

    @Autowired
    private FinApplyService finApplyService;
    @Autowired
    private FinApplyService2 finApplyService2;
    @Autowired
    private ZyzrNoticeService zyzrNoticeService;

    @Autowired
    private SignCheckService signCheckService;




    @RequestMapping (value="/finapply")
  //  public Map<String, String> finApply(@RequestBody FinApplyInfo finapplyinfo) throws IOException {
      public JSONObject finApply(@RequestBody FinApplyInfo finapplyinfo) throws IOException {
//    public Result finApply(@RequestParam Map<String,Object> supply_info,
//                                         HttpServletResponse response) throws IOException {
        log.info("test ok!");


        JSONObject checkresult = signCheckService.signCheck(finapplyinfo);
  //      String appid = finapplyinfo.get("appid");
        String code = checkresult.getString("code");
        if (!"0000".equals(code)){
            String msg = checkresult.getString("msg");
            JSONObject yresult =  YkgyjResult.failed(code,msg);
//            Map<String, String> signresult = signCheckService.signResult(yresult);
            return yresult;

        }

        Body supply_info = finapplyinfo.getBody();

        JSONObject result = finApplyService2.finApplyRecord2(supply_info);

 //       Map<String, String> signresult = signCheckService.signResult(result);

 //       log.info("reqdata"+supply_info);

//        Result result = zyzrNoticeService.zyzrRecord(apigw_rspdata);
//        log.info("reqdata"+result.toString());
        return result;

    }


}
