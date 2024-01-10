package com.icbc.match.entity;



import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @author  电子商务小额免密协议签订（无界面）
 *
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class PassfreePaymentSignV1 {

	/**
	 * 
	 * 20	商户ID，商户在工行开户商户档案时，由工行告知商户。
	 */
	private String merchant_id;
	
	/**
	 * 
	 * 商户入账账号
	 * 
	 */
	private String merchant_acct;
	/**
	 * 
	 * 
	 * 商户类型，1-线上POS档案商户；2-线下POS档案商
	 */
	private String merchant_type;
	/**
	 * 11	客户签订协议手机号（银行预留手机号）
	 * 
	 */
	private String mobile;
	/**
	 * 
	 * 19	客户需要签订协议的卡/账号
	 * 
	 */
	private String cardno;
	
	/**
	 * 30	客户姓名
	 * 
	 */
	private String cust_name;
	
	
	
	
	/**
	 * 客户证件类型，0-身份证；1-护照；2-军官证；3-士兵证；4-港澳台往来通行证；
	 * 5-临时身份证；6-户口簿；
	 * 7-其他；9-警官证；12-外国人永久居留证；21-边民出入境通行证
	 * 
	 */
	private String cust_cert_type;
	
	
	/**
	 * 18	客户证件号码
	 * 
	 */
	private String cust_cert_no;
	
	
	
	
	
	
	
	
}
