package com.icbc.match.controller;

import com.icbc.match.entry.RetEntry;
import com.icbc.match.service.MessageService;
import com.icbc.match.vo.CorpUserLogin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/exist")
public class AccountUserController {

    @Autowired
    private MessageService messageService;

    /**
     * 用户登录发送验证码
     *
     * @param corpUserLogin
     * @return
     */
    @PostMapping("/sendCaptcha")
    public RetEntry sendCaptcha(@RequestBody @Validated CorpUserLogin corpUserLogin) {

        //校验验证码
        return messageService.sendCaptcha(corpUserLogin);
    }

    /**
     * 用户登录核验验证码
     *
     * @param corpUserLogin
     * @return
     */
    @PostMapping("/validCaptcha")
    public RetEntry corpUserLogin(@RequestBody @Validated CorpUserLogin corpUserLogin) {

        //发送验证码
        return messageService.checkCaptcha(corpUserLogin);
    }

}
