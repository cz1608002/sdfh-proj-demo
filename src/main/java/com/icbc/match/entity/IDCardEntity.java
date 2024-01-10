package com.icbc.match.entity;

import lombok.Data;

@Data
public class IDCardEntity {
    private String name;
    private String sex;
    private String nation; //民族
    private String birthday;
    private String address; //地址
    private String code; //身份证号
    private String retain; //保留
    private String issuingAuthority;//签发机关
    private String limitedPeriod;//有效期限
    private String issuingDate; //签发日
    private String expringDate;//到期日
}
