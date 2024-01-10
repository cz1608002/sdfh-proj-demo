package com.icbc.match.entity;

import lombok.Data;

@Data
public class EPayParam {

    private String computeId; //客户端标志。21-ios客户端，22-android客户端
    private String areaCode; //地区信息，上送工行4位地区码，默认值北京0200
    private String userId; //userId必须上传，且每个用户的userId必须不同，需要根据userId查询当前用户的缴费明细等信息。送了相同userId的用户可以看到所有该userId下的缴费明细。
    private String payitemCode; //缴费项目编号
    private String apiTip; //自定义展示信息
    private String customerNo; //e钱包调用时必输，中国工商银行客户编号
    private String subAppId; //e钱包调用时必输，通过e钱包接入的合作方APPID
    private String returnUrl; //e钱包调用时必输，返回链接，用于返回接入页面

}
