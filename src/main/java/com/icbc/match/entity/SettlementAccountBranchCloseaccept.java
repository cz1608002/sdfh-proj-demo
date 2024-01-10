package com.icbc.match.entity;

import lombok.Data;

@Data
public class SettlementAccountBranchCloseaccept {

    private String corpNo;  //合作方机构编号
    private String trxAccDate;   // 合作方交易日期
    private String trxAccTime;   //     合作方交易时间
    private String corpDate;  //     合作方工作日期
    private String corpSerno;  //      合作方交易单号
    private String outServiceCode;   //       外部服务代码
    private String mediumId;  //      工行联名卡号
    private String secretKey;  //   密钥
    private String mediumIdHash;   //        工行联名卡号的哈希值
    private String callType;  //         请求类型(H5、API)

}
