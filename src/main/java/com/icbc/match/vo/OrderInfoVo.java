package com.icbc.match.vo;

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;

public class OrderInfoVo {

	@NotBlank(message = "订单号不能为空")
    private String orderId;

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	
}
