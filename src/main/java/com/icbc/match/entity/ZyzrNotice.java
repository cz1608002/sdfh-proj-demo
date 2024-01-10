package com.icbc.match.entity;


import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class ZyzrNotice {


    @TableId
    private String merId;
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
    private String note;
    private String notifyno;
    private String ntType;
    private String recAccno;
    private String recAccname;
    private String recBankname;
    private String ntDate;
    private String ntcount;
    private String fileUrl;
    private String apply_status;
    private String accountCode;


}
