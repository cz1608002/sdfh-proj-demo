package com.icbc.match.entity;

import lombok.Data;

@Data
public class SettlementAccountBranchScodeSend {

    private String corpNo;//合作方机构编号 true str(20) 20数字
    private String trxAccDate;//合作方交易日期 true str(10) 格式: 2017-03-02
    private String trxAccTime;//合作方交易时间 true str(8) 格式: 10:38:01
    private String corpDate;// 合作方工作日期 true str(10) 格式: 2017-03-02
    private String corpSerno;//合作方交易单号 true str(36) 格式: ABC123456789
    private String outServiceCode;//外部服务代码 true str(20) 示例: openaccount
    private String corpSernoOriginal;//原合作方交易单号 true str(36) 示例: ABC123456789

}
