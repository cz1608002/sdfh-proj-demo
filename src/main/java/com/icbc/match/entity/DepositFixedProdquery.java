package com.icbc.match.entity;

import lombok.Data;

@Data
public class DepositFixedProdquery {

    private String cmExternalEventNo;

    private String cmAppId;

    private String cmWorkdate;

    private String cmWorktime;

    private String qryType;

    private String start;

    private String rows;
    
    private String prodCode;
}
