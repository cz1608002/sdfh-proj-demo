package com.icbc.match.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class DebtorCode {
    @TableId
    private String merId;
    private String debtorCode;
    private String debtorName;
    private String bankInCode;
    private String bankCode;
    private String bankName;
}
