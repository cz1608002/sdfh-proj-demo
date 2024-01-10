package com.icbc.match.entity;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors
public class EpayPartnerRegisterSign {

	private String mobile; // 客户注册e支付协议的手机号（与发送短信手机号一致）
	private String cardno; // 客户需要签订e支付协议发送短信的合作方II、III类账号
	private String custName; // 客户姓名
	private String custCertType; // 客户证件类型，0-身份证；1-护照；2-军官证；3-士兵证；4-港澳台往来通行证；5-临时身份证；6-户口簿；7-其他；9-警官证；12-外国人永久居留证；21-边民出入境通行证
	private String custCertNo; // 客户证件号码
	private String shopAppId; // APP的编号,合作方在API开放平台注册时生成。如为合作方直接调用，则该字段可不输
	private String notifyFlag; // 是否接收通知消息：0-否；1-是；默认为0-否
	private String returnFlag; // 是否回到e钱包首页：0-否；1-是；默认为0-否
	private String userId; // 客户唯一标识

}
