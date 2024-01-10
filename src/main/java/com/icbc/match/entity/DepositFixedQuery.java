package com.icbc.match.entity;

import lombok.Data;

@Data
public class DepositFixedQuery {

    private String appId;

    private String workdate;

    private String worktime;

    private String externalEventNo;

    private String outServiceCode;

    private String secretKey;

    private String mediumId;

    private String queryFlag;

    private String pageNum;

    private String accNo;

    private String acCode;

    private String prodCode;

    private String fxseqNo;

    private String cashExFlag;

    private String ccy;
}
