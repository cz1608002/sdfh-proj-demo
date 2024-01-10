package com.icbc.match.entity;

import lombok.Data;

@Data
public class SettlementAccountBranchRecharge {

    private String corpNo;//     true 20 合作方机构编号 20数字
    private String trxAccDate;//          true 10 合作方交易日期 2017-03-02
    private String trxAccTime;//          true 8 合作方交易时间 10:38:01
    private String corpDate;//       true 10 合作方工作日期 2017-03-02
    private String corpSerno;//        true 36 合作方交易单号 ABC123456789
    private String outServiceCode;//              true 20 外部服务代码 recharge
    private String mediumId;//       true 44 工行联名卡号(支持加密，Base64编码,合作方上送需要加密) 6214760200000029162
    private String bindMedium;//         true 44 绑定的I类卡号(支持加密，Base64编码,合作方上送需要加密) 6214760200000029162
    private int ccy;//int true 3 币种 001
    private int cashExFlag;//int true 1 钞汇标志(0-钞，1-汇，2-假汇，3-境外汇款，9-不分钞汇) 0
    private String amount;//    true 17 交易额（后两位为小数位，100表示1元） 100000
    private String summary;//     true 20 摘要 -
    private String remarks;//     false 40 注释 -
    private String notifyAddr;//         false 300 通知地址 回调合作方服务地址
    private String secretKey;//        false 152 sm4对称密钥（对敏感信息加密时必送） -
    private String mediumIdHash;//            false 32 卡号哈希值 -
    private String bindMediumHash;//              false 32 绑定介质哈希值 -
    private int correctionFlag;//       int false 1 冲正标志 0-正常 3-双方红字冲帐
    private String correctionDate;//             false 10 冲正日期，如果correction_flag有输入并且不为0则必输 2017-03-02
    private String callType;//       false - 字典: API、H5，默认API，H5时敏感数据不加密 API

}
