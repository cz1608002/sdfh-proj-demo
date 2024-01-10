package com.icbc.match.entity;



import lombok.Data;

@Data

public class AccountReserve {
    private String zoneno;
    private String brno;
    private String mdCardno;//账号
    private String opType; //操作标志 1-保留 2-解保留 3-冻结 4-解冻 5-卡挂失
    private Integer holdCeil;//str(9) 保留金额
    private String chDate;  // str(10) 解保留时必输, 十位日期yyyy-mm-dd,送原保留日期
}

