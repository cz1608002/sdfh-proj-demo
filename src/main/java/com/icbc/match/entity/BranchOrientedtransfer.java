package com.icbc.match.entity;

import lombok.Data;

@Data
public class BranchOrientedtransfer {
    private String corpNo;//合作方机构编号,必输,20位数字 必输
    private String trxAccDate;// 合作方交易日期 yyyy-mm-dd 必输
    private String trxAccTime;// 合作方交易时间  hh:MM:ss 必输
    private String corpDate;//  合作方工作日期 yyyy-mm-dd 必输
    private String corpSerno;//合作方交易单号 36 必输
    private String outServiceCode;// 工外部服务代码 orientedtransfer 20 必输
    private String ccy;// 币种 3 必输
    private String mediumId;// 我行账号(支持加密，Base64编码) 44 必输
    private String cashExFlag;// 钞汇标志(0-钞，1-汇，2-假汇，3-境外汇款，9-不分钞汇) 1 必输
    private String amount;// 交易额（后两位为小数位，100表示1元） 17 必输
    private String oaccno;//  我行对手账号(支持加密，Base64编码)  44 必输
    private String drcrdirection;// 借贷方向（1-正向medium_id为借方，2-反向medium_id为贷方） 必输
    private String summary;// 摘要 20 必输
    private String remarks;// 注释 40 非必输
    private String notifyAddr;// 通知地址 300 非必输 152
    private String secretKey;// 使用SM2公钥加密的sm4对称密钥（对敏感信息加密时必送） 非必输
    private String omediumIdHash;// 他行卡号哈希值 32 非必输
    private String certType;//  证件类型  必输，只支持0-身份证
    private String certNo;// 证件号码(支持加密，Base64编码,若身份证号尾部里带有’x’请使用大写X) 必输
    private String custName;// 户名(支持加密，Base64编码) 88 必输
    private String callType;//  字典: API、H5，H5时敏感数据不加密 非必输
}
