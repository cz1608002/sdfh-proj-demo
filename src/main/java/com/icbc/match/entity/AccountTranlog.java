package com.icbc.match.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class AccountTranlog implements Serializable {
    private static final long serialVersionUID = 1L;
    private String serno;//商户ID
    private String userId;//商户名称
    private String mediumId;//二类户
    private String bindNo;//绑定卡号
    private String amount;//交易金额
    private String transCode;//交易类型1：充值  2：提现，3：解绑 ,4 定期购买,5.定期赎回'
    private String retCode;//API返回结果
    private String retMsg;//API返回信息
    private String appStatus;//结算类查询接口返回结果
    private Date transDate;//交易时间

}
