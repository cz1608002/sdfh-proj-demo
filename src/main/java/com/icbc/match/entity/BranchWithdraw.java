package com.icbc.match.entity;
import lombok.Data;

@Data
public class BranchWithdraw {
    private String corp_no;
    private String trx_acc_date;
    private String trx_acc_time;
    private String corp_date;
    private String corp_serno;
    private String out_service_code;
    private String medium_id;
    private  String bind_medium;
    private int ccy;
    private int cash_ex_flag;
    private String amount;
    private String summary;
    private String remarks;
    private String notify_addr;
    private String secret_key;
    private String medium_id_hash;
    private String bind_medium_hash;
    private int correction_flag;
    private String correction_date;
    private String call_type;

}
