package com.icbc.match.api.service.icbc;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.icbc.match.config.ApiConstants;
import com.icbc.match.entity.SettlementAccount;
import com.icbc.match.utils.UFCSendUtil;

/**
 * 结算账户退货
 * 
 * @author sdfh
 *
 */
@Service
public class SettlementAccountV1Refund extends UFCSendUtil {

	public Map<String, Object> execute(SettlementAccount settlementAccount) {
		return super.execute(settlementAccount, ApiConstants.API_SETTLEMENT_ACCOUNT_V1_REFUND);
	}
}
