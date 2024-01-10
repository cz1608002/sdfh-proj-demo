package com.icbc.match.controller;

import com.alibaba.fastjson.JSONObject;
import com.icbc.match.entity.Body;
import com.icbc.match.entity.FinApplyInfo;
import com.icbc.match.entry.Result;
import com.icbc.match.entry.YkgyjResult;
import com.icbc.match.service.PledgeLockService;
import com.icbc.match.service.SignCheckService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@Slf4j
@RestController
public class PledgeLockController {

    @Autowired
    private PledgeLockService pledgeLockService;


    @Autowired
    private SignCheckService signCheckService;
    @RequestMapping(value="/pledgelock")
    public JSONObject PledgeLock(@RequestBody FinApplyInfo pledgeLockinfo )throws IOException {
//    public Map<String,Object> zyzrNotice(@RequestParam Map<String,Object> apigw_rspdata,
//                                         HttpServletResponse response) throws IOException {
        log.info("test ok!");
//        log.info("reqdata"+body);
        JSONObject checkresult = signCheckService.signCheck(pledgeLockinfo);
  //      String appid = finapplyinfo.get("appid");
        String code = checkresult.getString("code");
        if (!"0000".equals(code)){
            String msg = checkresult.getString("msg");
            JSONObject yresult =  YkgyjResult.failed(code,msg);
 //           Map<String, String> signresult = signCheckService.signResult(yresult);
            return yresult;

        }
        Body body = pledgeLockinfo.getBody();
        JSONObject result = pledgeLockService.pledgeLock(body);

  //      Map<String, String> signresult = signCheckService.signResult(result);
//      log.info("reqdata"+result.toString());
        return result;

    }
}
