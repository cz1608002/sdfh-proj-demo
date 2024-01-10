package com.icbc.match.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class AccountBindCard implements Serializable {

    private static final long serialVersionUID = 1L;
    @TableId
    private String bindId;
    private String userId;
    private String bindNo;
    private String bankCode;
    private String mobileNo;
    private Date bindTime;
    private String status;
    private String origSerno;
    private String successFlag;

    private String certId;

    private String bindType;

}
