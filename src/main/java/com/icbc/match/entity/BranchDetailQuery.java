package com.icbc.match.entity;

import lombok.Data;

@Data
public class BranchDetailQuery {
    private String corp_no;
    private String trx_acc_date;
    private String trx_acc_time;
    private String corp_date;
    private String corp_serno;
    private String out_service_code;
    private String medium_id;
    private int ccy;
    private String begin_date;
    private String end_date;
    private int query_mode;
    private int page;
    private String medium_id_hash;
    private String secret_key;
    private String pn_busidate;
    private String pn_rowRecord;
    private String call_type;
}
