package com.icbc.match.entity;

import lombok.Data;

@Data
public class CertPersonalEntityEntityauth {

    private String reqId;         //申请编号，同一个应用每次调用应不同，格式由调用方自己定义 20190601038765435786
    private String channel;           //协议的渠道代码 2
    private String name;        //客户姓名 张三
    private String idCode;          //证件号码 412822198709675625
    private String photoData;            //客户头像数据(base64字符串形式) UGhvdG9zaG9wIDMuMAA4QklN
    private String scene;          //场景：01-政务，02-II、III类账务 01

}
