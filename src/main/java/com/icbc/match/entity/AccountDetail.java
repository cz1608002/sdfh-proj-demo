package com.icbc.match.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class AccountDetail {

    @TableId
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
    private String finCode;


}
