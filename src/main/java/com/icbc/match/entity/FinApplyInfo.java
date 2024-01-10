package com.icbc.match.entity;

import lombok.Data;

@Data
public class FinApplyInfo {

    private String appid;
    private String noce_str;
    private String time_stamp;
    private String sign;
    private Body body;


}
