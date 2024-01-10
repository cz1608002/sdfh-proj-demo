package com.icbc.match.entity;

import lombok.Data;

@Data
public class AccountMerchant {
    private String merchantId;
    private String merchantName;
    private String merchantAppid;
    private String zoneNo;
    private String brno;
    private String channelid;
    private String status;
    private String createTime;
}
