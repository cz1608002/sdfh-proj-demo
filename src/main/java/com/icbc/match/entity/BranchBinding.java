package com.icbc.match.entity;
import lombok.Data;

@Data
public class BranchBinding {
    private String corp_no;
    private String trx_acc_date;
    private String trx_acc_time;
    private String corp_date;
    private String corp_serno;
    private String out_service_code;
    private String corp_cis_no;
    private String corp_medium_id;
    private String medium_id;
    private String bind_medium;
    private int cert_type;
    private String cert_no;
    private String cust_name;
    private String mobile_no;
    private String notify_addr;
    private String secret_key;
    private String bind_medium_hash;
    private String medium_id_hash;

}
