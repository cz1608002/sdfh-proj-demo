package com.icbc.match.entity;

import lombok.Data;

@Data
public class DepositFixedRedeem {

    private String appId;

    private String workdate;

    private String worktime;

    private String externalEventNo;

    private String outServiceCode;

    private String secretKey;

    private String mediumId;

    private String prodCode;

    private String acCode;

    private String amount;

    private String cashexf;

    private String currType;
}
