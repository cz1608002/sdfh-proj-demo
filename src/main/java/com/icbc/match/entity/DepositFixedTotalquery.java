package com.icbc.match.entity;

import lombok.Data;

@Data
public class DepositFixedTotalquery {

    private String cmExternalEventNo;

    private String cmAppId;

    private String cmOutServiceCode;

    private String cmWorkdate;

    private String cmWorktime;

    private String cmSecretKey;

    private String mediumNo;
}
