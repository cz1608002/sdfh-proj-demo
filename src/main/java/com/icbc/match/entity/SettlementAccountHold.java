package com.icbc.match.entity;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 结算账户保留
 * 
 * @author sdfh
 *
 */
@Data
@Accessors(chain = true)
public class SettlementAccountHold {

	private String corp_no;// 合作方机构编号
	private String trx_acc_date;// 合作方交易日期
	private String trx_acc_time;// 合作方交易时间
	private String corp_date;// 合作方工作日期
	private String corp_serno;// 合作方交易单号
	private String out_service_code;// 外部服务代码
	private String medium_id;// 工行联名卡号
	private int ccy;// 币种
	private int cash_ex_flag;// 钞汇标志(0-钞，1-汇，2-假汇，3-境外汇款，9-不分钞汇)
	private String amount;// 交易额（后两位为小数位，100表示1元）
	private int hold_type;// 原认筹保留种类，送默认值
	private String summary;// 摘要

}
