package com.icbc.match.entity;

import com.alibaba.fastjson.JSONArray;
import lombok.Data;

import java.util.Map;
@Data
public class FinApply {


    private String merId;
    private String timestamp;
    private String fseqno;
    private String field1;
    private String field2;
    private String field3;

    private String finCode;
    private String debtorCode;
    private String debtorName;
    private String bankInCode;
    private String creditCode;
    private String creditName;
    private String customerId;
    private String bankCode;
    private String bankName;
    private String ntType;
    private String recAccno;
    private String recAccname;
    private String recBankname;
    private String ntDate;
    private String count;

    private JSONArray accountList;
    private String accountCode;
}
