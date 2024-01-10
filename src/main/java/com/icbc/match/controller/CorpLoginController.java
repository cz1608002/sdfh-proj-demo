package com.icbc.match.controller;


import com.icbc.match.entry.Result;
import com.icbc.match.service.CorpLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@Controller
public class CorpLoginController {

    private String serverPrefix;

    @Autowired
    private CorpLoginService corpLoginService;



    @RequestMapping("/custlogin/V1")
    public void corpLogin(@RequestParam(value = "biz_content", required = false) String bizContent,
                          HttpServletResponse response) throws IOException {
        Result result = corpLoginService.corpLogin(bizContent); //执行登录逻辑，异常情况由框架统一处理

        Map<String, Object> param = result.getParams();
        String redict = (String) param.get("redirect");

        response.sendRedirect(serverPrefix + redict); //重定向至前端
    }



}




