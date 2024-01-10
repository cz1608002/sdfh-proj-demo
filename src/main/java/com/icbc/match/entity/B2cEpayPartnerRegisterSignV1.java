package com.icbc.match.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


/**
 * @author .合作方E支付注册界面服务
 *
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class B2cEpayPartnerRegisterSignV1 {

	
	/**
	 * 11	客户注册e支付协议的手机号（与发送短信手机号一致）
	 * 
	 */
	private String mobile;
	/**
	 * 19	客户需要签订e支付协议发送短信的合作方II、III类账号
	 * 
	 */
	private String cardno;
	/**
	 * 30	客户姓名
	 * 
	 */
	private String cust_name;
	
	/**
	 * 2	客户证件类型，0-身份证；1-护照；2-军官证；3-士兵证；4-港澳台往来通行证；5-临时身份证；6-户口簿；7-其他；9-警官证；
	 * 12-外国人永久居留证；21-边民出入境通行证
	 * 
	 */
	private String cust_cert_type;
	/**
	 * 18	客户证件号码
	 * 
	 */
	private String cust_cert_no;
	

	
}
