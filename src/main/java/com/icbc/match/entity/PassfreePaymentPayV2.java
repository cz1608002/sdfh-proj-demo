package com.icbc.match.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @author 电子商务小额免密支付
 *
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class PassfreePaymentPayV2 {

	/**
	 * 
	 * 交易日期时间。格式为：YYYYMMDDHHmmss
	 */
	private String order_date;
	
	
	/**
	 * 
	 * 订单号 30
	 */
	private String order_id;
	
	/**
	 * 
	 * 订单金额  10
	 */
	private String amount;
	
	/**
	 * 
	 * 商户ID 20
	 */
	private String merchant_id;
	
	/**
	 * 32
	 * 商户签约号 agreement_no，cardno三者必输其一。
	 */
	private String external_agreement_no;
	
	/**
	 * 
	签约时工行生成的协议编号  64 
	 */
	private String agreement_no;
	
	
	/**
	 * 
	19	签约卡号，与external_agreement_no，agreement_no三者必输其一。
	 */
	private String cardno;
	
	/**
	 *商户入账账号 
	 * 
	 * 
	 */
	private String merchant_acct;
	
	/**
	 * 1	商户类型，1-线上POS档案商户；2-线下POS档案商户。
	 */
	private String merchant_type;
	
	
	
}
