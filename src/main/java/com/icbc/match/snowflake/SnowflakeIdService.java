package com.icbc.match.snowflake;

import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * 雪花算法全局唯一ID生成服务
 */
@Service
public class SnowflakeIdService {

    private static final long WORK_ID = 0; //工作机器ID,本演示应用为单机版，故工作机器ID为0
    private static final long DATACENTER_ID = 0; //数据中心ID,本演示应用为单机版，故 数据中心ID为0

    private SnowflakeIdWorker worker;

    @PostConstruct
    public void init() {
        worker = new SnowflakeIdWorker(WORK_ID, DATACENTER_ID);
    }

    public String nextId() {
        return String.valueOf(worker.nextId());
    }

}
