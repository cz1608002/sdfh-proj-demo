package com.icbc.match.entity;

import lombok.Data;

@Data
public class SettlementAccountBranchScodeVerify {

    private String corpNo;// str true 20 合作方机构编号，我行分配的机构编号 20数字
    private String trxAccDate;//      str true 10 合作方交易日期 2017-03-02
    private String trxAccTime;//      str true 8 合作方交易时间 10:38:01
    private String corpDate;//      str true 10 合作方工作日期 2017-03-02
    private String corpSerno;//      str true 36 合作方交易单号 ABC123456789
    private String corpSernoOriginal;//        str true 36 原合作方交易单号 ABC123456789
    private String outServiceCode;//        str true 20 外部服务代码 scodeverify
    private String smsSendNo;//   str false 6 短信验证码编号 100291
    private String smsScode;// str false 6 短信验证码 6位短信验证码
    private String callType;//   str false - 字典: “API”、”H5” 默认为API

}
