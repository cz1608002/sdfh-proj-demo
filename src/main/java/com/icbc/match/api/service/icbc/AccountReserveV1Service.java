package com.icbc.match.api.service.icbc;

import com.icbc.match.api.client.ApiClientService;
import com.icbc.match.config.ApiConstants;
import com.icbc.match.entity.AccountReserve;
import com.icbc.match.snowflake.SnowflakeIdService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class AccountReserveV1Service {
    @Autowired
    private ApiClientService apiClientService;
    @Autowired
    private static SnowflakeIdService idService;
    /*
     对账户余额的保留和解保留处理
     */
    public Map<String, Object> accountReserve(AccountReserve accountReserve) {

        Map<String, Object> response = null;
        Map<String, Object> params = new HashMap<String, Object>();
        Map<String, Object> paramsPublic = new HashMap<String, Object>();
        Map<String, Object> paramsChannel = new HashMap<String, Object>();
        Map<String, Object> paramsCommmang = new HashMap<String, Object>();
        Map<String, Object> paramsP8120com = new HashMap<String, Object>();

        String id = idService.nextId();

        paramsPublic.put("zoneno",accountReserve.getZoneno());
        paramsPublic.put("brno",accountReserve.getBrno());
        paramsPublic.put("servface","10");
        paramsPublic.put("tellerno","10");
        paramsCommmang.put("optype",accountReserve.getOpType());
        paramsCommmang.put("currtype","1");
        paramsCommmang.put("ckusr_f","0");
        paramsCommmang.put("ckpin_f","0");
        paramsCommmang.put("trxcode","00000");
        paramsCommmang.put("termid","14");
        paramsP8120com.put("mdcardno",accountReserve.getMdCardno());
        paramsP8120com.put("holdtype","8");
        paramsP8120com.put("holdceil",accountReserve.getHoldCeil());
        paramsChannel.put("chan_type","203");
        paramsChannel.put("chan_serialno","9405"+accountReserve.getZoneno()+id);
        paramsChannel.put("oapp","f-rfbp");
        params.put("public", paramsPublic);
        params.put("channel", paramsChannel);
        params.put("commmang", paramsCommmang);
        params.put("p8120com", paramsP8120com);

        response = apiClientService.syncInvokeReqBodyJson(ApiConstants.API_CSI_ACCOUNT_V1_RESERVE,params);
        if (response != null) {
            log.debug("value:{}", response);
        }
        //返回chdate

        return response;
    }

}
