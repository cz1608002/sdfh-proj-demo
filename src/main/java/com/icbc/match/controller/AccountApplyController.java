package com.icbc.match.controller;

import com.icbc.match.entry.RetEntry;
import com.icbc.match.service.AccountApplyService;
import com.icbc.match.vo.CaptchaSendVo;
import com.icbc.match.vo.CaptchaVerifyVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/apply")
@Slf4j
public class AccountApplyController {

    @Autowired
    private AccountApplyService accountApplyService;

    /**
     * 调用开卡接口发送验证码
     *
     * @param captchaVo
     * @return
     */
   /* @PostMapping("/sendCaptcha")
    public RetEntry sendCaptcha(@RequestBody @Validated CaptchaSendVo captchaVo) {

        return accountApplyService.accountOpen(captchaVo);

    }

    *//**
     * 验证码提交，核验，开卡
     *
     * @param verifyVo
     * @return
     *//*
    @PostMapping("/validCaptcha")
    public RetEntry varifyCaptcha(@RequestBody @Validated CaptchaVerifyVo verifyVo) {
        return accountApplyService.verifyCaptcha(verifyVo);
    }
*/

}
