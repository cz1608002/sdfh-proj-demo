package com.icbc.match.api.client;

import com.icbc.api.*;
import com.icbc.match.snowflake.SnowflakeIdService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 对API SDK进行二次封装，将无界面接口和有界面接口封装为Serivice
 */
@Service
@Slf4j
public class ApiClientCopService {

    @Autowired
    private UiIcbcClient uiIcbcClient; //注入UiIcbcClient

    @Autowired
    private DefaultIcbcClient defaultIcbcClient; //注入DefaultIcbcClient

    @Autowired
    private SnowflakeIdService snowflakeIdService; //基于雪花算法的唯一ID生成服务

    /**
     * 调用无界面API接口
     *
     * @param request 接口上送参数
     * @return 接口响应参数
     * @throws IcbcApiException
     */
    public <T extends IcbcResponse> T execute(IcbcRequest<T> request) throws IcbcApiException {
        //基于雪花算法生成MsgId
        String msgId = String.valueOf(snowflakeIdService.nextId());

        log.info("api request->msg id is [{}]", msgId);

        //依赖DefaultIcbcClient完成调用
        return defaultIcbcClient.execute(request, msgId);
    }

    /**
     * 生成有界面API接口表单
     *
     * @param request 接口上送参数
     * @return 表单HTML
     * @throws IcbcApiException
     */
    public String buildPostForm(IcbcRequest<?> request) throws IcbcApiException {
        //基于雪花算法生成MsgId
        String msgId = String.valueOf(snowflakeIdService.nextId());

        log.info("api request->msg id is [{}]", msgId);

        //依赖UIIcbcClient完成调用
        return uiIcbcClient.buildPostForm(request, msgId);
    }

}
