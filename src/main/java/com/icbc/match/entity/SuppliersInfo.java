package com.icbc.match.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class SuppliersInfo {
    @TableId
    private String applyName;
    private String applyCode;
    private String apply_corp_name;
    private String apply_corp_id_num;
    private String contactName;
    private String contact_tel;
    private String address;
    private String social_code;
    private String credit_code;
    private String tax_code;

    private String status;
    private String customerId;
    private String bankCode;

    private String bankName;
    private String finProductCode;
    private String make_credit_date;
    private String creditflag;
    private String belongTo;
    private String instname;
    private String accno;
    private String accname;
    private String openbank;
    private String creditAmt;
    private String maxPeriod;
    private String tbStatus;


}
