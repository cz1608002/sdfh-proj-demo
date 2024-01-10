package com.icbc.match.controller;



import com.alibaba.fastjson.JSONObject;
import com.icbc.api.internal.util.internal.util.fastjson.JSON;
import com.icbc.match.entity.Notice;
import com.icbc.match.entry.Result;
import com.icbc.match.entry.YkgyjResult;
import com.icbc.match.service.CorpLoginService;
import com.icbc.match.service.ZyzrNoticeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@Slf4j
@RestController
public class ZyzrNoticeController {


    @Autowired
    private ZyzrNoticeService zyzrNoticeService;



    @RequestMapping(value="/zyzrnotice")
    public Map<String,Object> zyzrNotice(@RequestParam(value = "apigw_rspdata", required = false) String apigwrspdata,
                          HttpServletResponse response) throws IOException {
//    public Map<String,Object> zyzrNotice(@RequestParam Map<String,Object> apigw_rspdata,
//                                         HttpServletResponse response) throws IOException {
        log.info("test ok!");
        log.info("reqdata"+apigwrspdata);
        Map<String,Object> result = zyzrNoticeService.zyzrRecord(apigwrspdata);
//      log.info("reqdata"+result.toString());
        return result;

    }


}
