package com.icbc.match.entity;

import lombok.Data;

@Data
public class SettlementAccountBranchQueryApply {

    private String corpNo;// 合作方机构编号 20数字
    private String corpSerno;// 合作方交易单号 ABC123456789
    private String outServiceCode;// 外部服务代码 queryapply
    private String oriCorpSerno;// 原合作方交易单号

}




