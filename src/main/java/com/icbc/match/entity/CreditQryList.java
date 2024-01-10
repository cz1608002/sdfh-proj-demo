package com.icbc.match.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CreditQryList {


    @TableId
    private String finCode;
    private String debtorCode;
    private String debtorName;
    private String bankInCode;
    private String creditCode;
    private String creditName;
    private String customerId;
    private String bankCode;
    private String bankName;
    private String finProductCode;
    private BigDecimal finAmt;
    private int finDays;

    private BigDecimal finRate;
    private BigDecimal finRatio;
    private String contactName;
    private String mobile;
    private String accCount;
    private String ntType;
    private String recAccno;
    private String recAccname;
    private String recBankname;
    private String ntDate;
    private String ntcount;
    private String fielUrl;
    private String apply_status;

    private String merid;
    private String debtor_code;
    private String debtor_name;

    private String credit_code;
    private String credit_name;
    private String customer_id;
    private String bank_in_code;
    private String bank_code;
    private String bank_name;
    private String product_code;
    private String accountCode;

    private BigDecimal accountAmt;
    private String accStartDate;
    private String accEndDate;
    private String invoiceNo;
    private String contractNo;
    private String orderNo;
    private String accountYear;
    private String accountStatus;
    private String note;
    private String loanStatus;
    private String repayStatus;
    private String notifyno;

}
