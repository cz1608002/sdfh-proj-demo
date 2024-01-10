package com.icbc.match.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @author 工银e钱包服务
 *
 */

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class EaccountManageV1 {

	/**
	 * 20	客户唯一标识
	 * 
	 */
	private String userId;
	
	/**
	 * 20	合作方APPID
	 * 
	 */
	private String corpAppid;
	
	
	
	/**
	 * 19	交易时间 1538121517482
	 * 
	 */
	private String orderTimeStamp;
	
	
	
}
