package com.icbc.match.controller;

import com.icbc.match.entry.RetEntry;
import com.icbc.match.service.AccountApplyService;
import com.icbc.match.vo.CaptchaSendVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/account")
@Slf4j
public class AccountInfoController {

    @Autowired
    private AccountApplyService accountApplyService;

    /**
     * 查询卡信息
     *
     * @param captchaVo
     * @return
     */
   /* @PostMapping("/info")
    public RetEntry query(@RequestBody @Validated CaptchaSendVo captchaVo) {

//        return accountApplyService.accountOpen(captchaVo);

    }*/
}
