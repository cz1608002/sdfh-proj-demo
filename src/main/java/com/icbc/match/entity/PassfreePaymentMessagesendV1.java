package com.icbc.match.entity;



import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @author 电子商务小额免密协议签订短信发送
 *
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class PassfreePaymentMessagesendV1 {

	/**
	 * 20	商户ID
	 * 
	 */
	private String merchant_id;
	
	
	/**
	 * 1	商户类型，1-线上POS档案商户；2-线下POS档案商户
	 * 
	 */
	private String merchant_type;
	/**
	 * 11	客户签订协议手机号（银行预留手机号）
	 * 
	 */
	private String mobile;
	
	/**
	 *19	客户需要签订协议的卡/账号
	 * 
	 */
	private String cardno;
	/**
	 * 50	合作方商户名称
	 */
	private String shop_name;
	

	
}
