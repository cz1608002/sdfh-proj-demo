package com.icbc.match.entity;


import lombok.Data;

@Data
public class BranchBindingQuery {
    private String corpNo;//合作方机构编号,必输,20位数字
    private String trxAccDate;// 合作方交易日期 yyyy-mm-dd
    private String trxAccTime;// 合作方交易时间  hh:MM:ss
    private String corpDate;//  合作方工作日期 yyyy-mm-dd
    private String corpSerno;//  外部服务代码 unbinding
    private String outServiceCode;// 工行联名卡号
    private String mediumId;// 合作方交易时间
    private String secretKey;// 使用SM2公钥加密的sm4对称密钥，使用密文时必输，Base64编码
}
