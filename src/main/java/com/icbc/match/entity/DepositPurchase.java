package com.icbc.match.entity;

import lombok.Data;


@Data
public class DepositPurchase {

    private String appId;

    private String workdate;

    private String worktime;

    private String externalEventNo;

    private String outServiceCode;

    private String secretKey;

    private String mediumId;

    private String prodCode;

    private String term;

    private String cashExFlag;

    private String ccy;

    private String amount;

    private String lgldoctp;

    private String idcode;

    private String name;
}
