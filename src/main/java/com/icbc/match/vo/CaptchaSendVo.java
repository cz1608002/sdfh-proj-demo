package com.icbc.match.vo;

import lombok.Data;

@Data
public class CaptchaSendVo {

    private String flag;

    private String bindMedium;

    private String certNo;
    private String custName;
    private String mobileNo;
    private String address;

    private int foreverFlag; //长期标识
    private String validityPeriod; //有效期至
    private String signDate; //签发日期
    private String marketNo;
    private int occupation; //职业

    private String idcardFace; //身份证正面

    private String idcardEmblem; //身份证反面

}
