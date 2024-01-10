package com.icbc.match.entity;



import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @author 电子商务小额免密协议查询
 *
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class PassfreePaymentQueryV2 {

	//YYYYMMDDHHmmss 交易时间
	private String order_date;
	// 订单号 20161029000111
	private String order_id;
	// 订单金额
	private String amount;
	//商户id
	private String merchant_id;
	/**
	 * 商户签约号，代扣协议中标示用户的唯一签约号（确保在商户系统中唯一）。
	 * 格式规则：支持大写小写字母和数字，最长32位。
	 * 与agreement_no，cardno三者必输
	 */
	private String external_agreement_no;
	
	/**
	 * 签约时工行生成的协议编号。格式规则：支持大写小写字母和数字，最长64位。
	 * 与external_agreement_no，cardno三者必输其一。
	 */
	private String agreement_no;
	
	/**
	 * 签约卡号，与external_agreement_no，agreement_no三者必输其一
	 */
	private String cardno;
	
	/**
	 * 商户入账账号，只能交易时指定。
	 */
	private  String merchant_acct;
	
	/**
	 * 
	 * 商户类型，1-线上POS档案商户；2-线下POS档案商户。
	 */
	private String merchant_type;
	
	
}
