package com.icbc.match.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.icbc.match.entry.RetEntry;
import com.icbc.match.service.AccountTransferService;
import com.icbc.match.vo.TransferVo;

/**
 *
 */
@RestController
@RequestMapping("/api/account/transfer")
public class AccountTransferController {

	@Autowired
	private AccountTransferService transferService;

	/**
	 * 充值提现
	 * 
	 * @param transferVo
	 * @return
	 */
	@PostMapping("/operation")
	public RetEntry transfer(@RequestBody @Validated TransferVo transferVo) {

		return transferService.transfer(transferVo);
	}

}
