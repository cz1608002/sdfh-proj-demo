package com.icbc.match.vo;

import org.hibernate.validator.constraints.NotBlank;

import lombok.Data;

@Data
public class TransferVo {

	@NotBlank(message = "银行卡号不能为空")
	private String bindNo;// 银行卡号

	@NotBlank(message = "金额不能为空")
	private String amount;// 金额

	@NotBlank(message = "操作类型不能为空")
	private String type;// 类型 1充值 2提现

}
