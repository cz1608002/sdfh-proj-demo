package com.icbc.match.service;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.gson.Gson;
import com.icbc.match.api.service.icbc.BranchDetailQueryV1Service;
import com.icbc.match.config.ApiConstants;
import com.icbc.match.entity.AccountCard;
import com.icbc.match.entity.BranchDetailQuery;
import com.icbc.match.entry.RetEntry;
import com.icbc.match.mapper.AccountCardMapper;
import com.icbc.match.mapper.AccountUserMapper;
import com.icbc.match.security.IcbcSmService;
import com.icbc.match.utils.TokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
@Slf4j
public class AccountTranlogService {

    @Autowired
    private BranchDetailQueryV1Service branchDetailQueryV1Service;


    @Autowired
    private IcbcSmService icbcSmService;

    @Autowired
    private AccountCardMapper accountCardMapper;

    @Autowired
    private AccountUserMapper accountUserMapper;

    /**
     * 查询二类户交易明细
     *
     * @return
     */
    public RetEntry queryHistory() {

        Gson gson = new Gson();
        String userId = TokenUtil.getUserId();

        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("USER_ID", userId);
        AccountCard accountCard = accountCardMapper.selectOne(queryWrapper);

        Map<String, String> map = new HashMap<>();
        map.put("mediumId", accountCard.getMediumId());
        map.put("bindMedium", accountCard.getBindMediumId());
        map = icbcSmService.encrypt(map);

        String serno = "FHJS025" + IdUtil.simpleUUID().substring(0, 6);


        BranchDetailQuery branchDetailQuery = new BranchDetailQuery();
        branchDetailQuery.setCorp_no(ApiConstants.CORP_NO);
        branchDetailQuery.setTrx_acc_date(ApiConstants.ACC_DATE);
        branchDetailQuery.setTrx_acc_time("12:30:21");
        branchDetailQuery.setCorp_date(ApiConstants.ACC_DATE);
        branchDetailQuery.setCorp_serno(serno);
        branchDetailQuery.setOut_service_code("querydetail");
        branchDetailQuery.setMedium_id(map.get("mediumId"));
        branchDetailQuery.setCcy(1);
        branchDetailQuery.setBegin_date("2020-12-03");
        branchDetailQuery.setEnd_date("2020-12-30");
        branchDetailQuery.setQuery_mode(1);
        branchDetailQuery.setPage(1);
        branchDetailQuery.setMedium_id_hash("");
        branchDetailQuery.setSecret_key(map.get("secretKey"));
        branchDetailQuery.setCall_type("API");

        log.info("request请求参数:" + branchDetailQuery.toString());
        Map result = branchDetailQueryV1Service.branchDetailquery(branchDetailQuery);
        log.info(gson.toJson(result));

        List<Map<String, String>> list = (List) result.get("order_detail");

        String secretKey = result.get("secret_key").toString();

        for (Map map1 : list) {
            Map smMap = new HashMap();
            if (!StringUtils.isEmpty(map1.get("cp_medium_id"))) {
                smMap.put("cp_medium_id", map1.get("cp_medium_id"));
            }
            if (!StringUtils.isEmpty(map1.get("resav_name"))) {
                smMap.put("resav_name", map1.get("resav_name"));
            }
            if (!StringUtils.isEmpty(map1.get("medium_id"))) {
                smMap.put("medium_id", map1.get("medium_id"));
            }
            smMap.put("secretKey", secretKey);
            smMap = icbcSmService.decrypt(smMap);

            map1.put("cp_medium_id", smMap.get("cp_medium_id"));
            map1.put("resav_name", smMap.get("resav_name"));
            map1.put("medium_id", smMap.get("medium_id"));

            String cp_medium_id = (String) map1.get("cp_medium_id");
            accountUserMapper.selectUserList(cp_medium_id);

        }

        log.info("解密报文：{}", gson.toJson(list));

        return RetEntry.getOneOkRetEntry().addParam("list", list);


    }

}
