package com.icbc.match.entity;

import lombok.Data;

@Data
public class SettlementAccountBranchOpen {

    private String corpNo; //true , 合作方机构编号，我行分配的机构编号 ,12位数字
    private String trxAccDate; //true , 合作方交易日期, 2017-03-02
    private String trxAccTime; //true , 合作方交易时间,10:38:01
    private String corpDate; //true , 合作方工作日期, 2017-03-02
    private String corpSerno; //true , 合作方交易单号,ABC123456789
    private String outServiceCode; //true , 外部服务代码,openaccount
    private String corpMediumId; //true , 合作方介质号,如微信号
    private int certType; //true , 证件类型,只支持0-身份证
    private String certNo; //true , 证件号码(支持加密，Base64编码,若身份证号尾部里带有'x'请使用大写X),身份证号码
    private String custName; //true , 户名(支持加密，Base64编码),张三
    private String mobileNo; //true , 手机号，只支持11位，不加区号等(支持加密，Base64编码),13820251234
    private int occupation; //true , 职业,1-公务员;2-事业单位员工;3-公司员工;4-军人警察;5-工人;6-农民;7-管理人员;8-技术人员;9-私营业主; 10-文体明星;11-自由职业者;12-学生; 13-无职业;|
    private int foreverFlag; //true , 证件长期有效标志, 0-否(默认)，1-是
    private String signDate; //true , 证件签发日期 ,1900-01-01
    private String corpCisNo; //false, 合作方客户号,客户在合作方的客户ID
    private String bindMedium; //false, 绑定的I类卡号(支持加密，Base64编码，绑卡开户必输),6214760200000029162
    private int gender; //false, 性别,1-男 2-女
    private int nationality; //false, 国籍,156-中国
    private String validityPeriod; //false, 证件失效日期（当forever_flag为0时必输）,1900-01-01
    private String birthday; //false, 出生日期, 1900-01-01
    private String issuingAuthority; //false, 发证机关,XXXXXX
    private String address; //false, 地址,XXX路XXX号
    private int taxDeclarationFlag; //false, 申税标志 , 1-是 2-否
    private String customizationInfo; //false, 合作方上送的客户定制化信息
    private String notifyAddr; //false, 通知地址, 回调合作方服务地址
    private String secretKey; //false, 使用SM2公钥加密的sm4对称密钥，使用密文时必输，Base64编码
    private String bindMediumHash; //false, 绑定的I类卡号的哈希值，目前是通过md5生成，英文统一小写,
    private String callType; //false, 字典: "API"、"H5" ,默认为API
    private int isOpenEpay; //false, 是否开通e支付（默认为否，需要配置产品协议参数）, 0-否，1-是
    private int isOpenQpay; //false, 是否开通快捷支付（默认为否，需要配置产品协议参数）, 0-否，1-是
    private String qpayCorpNo; //false, 四位我行快捷支付机构号（当is_open_qpay为1时必输）, 0147（财付通）
    private int imgUploadStatus; //false, 证件影像上送状态, 0-未上送(默认)，1-已上送
    private String sellerCode; //false, 营销代码
    private String indentifySerno; //false, 分行鉴权单号
    private String accountOrgNo; //false, 合作方指定归属机构
    private String openPlace; //false, 开户场所
    private String equipmentId; //false, 设备ID
    private String ip; //false, IP地址
    private String mac; //false, MAC地址
    private String province; //false, 省（仅在协议条件参数l_account_org_type为4时必输有用）
    private String city; //false, 市/区县（仅在协议条件参数l_account_org_type为4时必输有用）


}
