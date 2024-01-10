package com.icbc.match.vo;

import lombok.Data;

@Data
public class CorpUserLogin {

    private String flag;
    private String sendTo;
    private String captcha;
    private String messageCode;

}
