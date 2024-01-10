package com.icbc.match.service;

import static com.icbc.match.config.SecurityConstants.HEADER_STRING;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.google.gson.Gson;
import com.icbc.apip.exception.InvokerException;
import com.icbc.apip.invoker.Invokers;
import com.icbc.match.api.service.icbc.SettlementAccountBranchQueryOpenV1Service;
import com.icbc.match.config.ApiConstants;
import com.icbc.match.config.Apollo;
import com.icbc.match.entity.AccountCard;
import com.icbc.match.entity.AccountUser;
import com.icbc.match.entity.SettlementAccountBranchQueryOpen;
import com.icbc.match.entry.Result;
import com.icbc.match.entry.RetEntry;
import com.icbc.match.entry.SecurityUser;
import com.icbc.match.exception.TransactionException;
import com.icbc.match.mapper.AccountCardMapper;
import com.icbc.match.mapper.AccountUserMapper;
import com.icbc.match.security.JwtTokenService;
import com.icbc.match.snowflake.SnowflakeIdService;
import com.icbc.match.utils.CheckResultUtil;
import com.icbc.match.utils.RSAUtils;
import com.icbc.match.utils.SpringBootRequestUtil;
import com.icbc.match.utils.TokenUtil;
import com.icbc.match.vo.CorpUserLogin;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class MessageService {

	private String appId;

	private String messageId;

	private String messageUrl;

	private String priKey;

	@Autowired
	private JwtTokenService jwtTokenService;

	@Autowired
	private AccountUserService accountUserService;

	@Autowired
	private AccountUserMapper accountUserMapper;

	@Autowired
	private SettlementAccountBranchQueryOpenV1Service settlementAccountBranchQueryOpenV1Service;

	@Autowired
	private SnowflakeIdService snowflakeIdService;

	@Autowired
	private AccountCardMapper accountCardMapper;

	/**
	 * 调用分行服务发送验证码
	 *
	 * @param corpUserLogin
	 * @return
	 */
	public RetEntry sendCaptcha(CorpUserLogin corpUserLogin) {

		Gson gson = new Gson();
		String mobile = corpUserLogin.getSendTo();
		Map map = new HashMap();
		map.put("sendTo", mobile);
		map.put("messageId", messageId);
		map.put("appId", appId);
		String data = gson.toJson(map);
		String sign = "";
		String encodedData = "";
		try {
			encodedData = URLEncoder.encode(data, "utf-8");
			sign = RSAUtils.sign(encodedData, priKey);
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new TransactionException("-1", "");
		}
		MultiValueMap<String, Object> params = new LinkedMultiValueMap<>();
		params.add("bizContent", encodedData);
		params.add("sign", sign);
		String url = messageUrl + "/message/captcha/send";
		Result result = SpringBootRequestUtil.sendPostRequest(url, params);
		CheckResultUtil.checkResult(result);

		AccountUser accountUser = accountUserService.corpUserInfo(mobile);
		SecurityUser securityUser = new SecurityUser();
		if (accountUser == null) {
			String userId = IdUtil.simpleUUID();
			securityUser.setMobile(corpUserLogin.getSendTo());
			securityUser.setUuid(userId);

			// 保存新客户客户
			accountUser.setUserId(userId);
			accountUser.setMobile(mobile);
			accountUser.setCreateTime(DateUtil.date());

			accountUserMapper.insert(accountUser);
		} else {
			securityUser.setMobile(corpUserLogin.getSendTo());
			securityUser.setUuid(accountUser.getUserId());
			securityUser.setMobile(accountUser.getMobile());

			// 查询老客户账号状态
			SettlementAccountBranchQueryOpen settlementAccountBranchQueryOpen = new SettlementAccountBranchQueryOpen();
			settlementAccountBranchQueryOpen.setCorpNo(ApiConstants.CORP_NO);
			settlementAccountBranchQueryOpen.setOutServiceCode("queryopenbook");
			settlementAccountBranchQueryOpen.setOpenCorpSerno(accountUser.getOrigSerno());
			settlementAccountBranchQueryOpen.setCallType("API");

			Map<String, Object> resultMap = settlementAccountBranchQueryOpenV1Service
					.execute(settlementAccountBranchQueryOpen);

			int returnCode = -1;
			String returnMsg = "";
			try {
				returnCode = Invokers.getReturnCode(resultMap);
				returnMsg = Invokers.getReturnMsg(resultMap);
			} catch (InvokerException e) {
				log.error(e.getMessage());
				throw new TransactionException(String.valueOf(returnCode), returnMsg);
			}

			// 卡状态不正常则修改状态
			if (returnCode != 0) {
				AccountCard accountCard = new AccountCard();
				accountCard.setStatus(String.valueOf(returnCode));
				accountCard.setUserId(accountUser.getUserId());
				accountCardMapper.updateById(accountCard);
			}
		}
		String token = jwtTokenService.generateToken(securityUser);
		return RetEntry.getOneOkRetEntry().addParam(HEADER_STRING, token);
	}

	/**
	 * 调用分行服务核验验证码 验证成功返回用户信息
	 *
	 * @param corpUserLogin
	 * @return
	 */
	public RetEntry checkCaptcha(CorpUserLogin corpUserLogin) {

		MultiValueMap<String, Object> params = new LinkedMultiValueMap<>();
		String mobile = corpUserLogin.getSendTo();
		params.add("mobile", mobile);
		params.add("captcha", corpUserLogin.getCaptcha());
		params.add("messageCode", corpUserLogin.getMessageCode());
		String url = messageUrl + "/message/captcha/check";
		Result result = SpringBootRequestUtil.sendPostRequest(url, params);

		CheckResultUtil.checkResult(result);

		String userId = TokenUtil.getUserId();

		return RetEntry.getOneOkRetEntry().addParam("list", accountUserMapper.selectUserList(userId));
	}
}
