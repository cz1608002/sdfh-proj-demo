package com.icbc.match.entity;



import lombok.Data;

@Data

public class AccountStatusQuery {
    private String outServiceCode; //外部服务代码,必输，最大20
    private String corpSerno;// 交易单号，必输，最大 36
    private String corpNo;//合作方机构编号,必输,20位数字
    private String corpMediumId;// 合作方介质号 ,必输,最大44
    private String callType;// 字典: API、H5，默认API，H5时敏感数据不加密
}

