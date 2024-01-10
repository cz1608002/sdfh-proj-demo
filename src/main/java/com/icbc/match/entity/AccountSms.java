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
public class AccountSms implements Serializable {
    private static final long serialVersionUID = 1L;
    @TableId
    private String userId;//用户ID
    private String serno;//本次流水号
    private String origSerno;//原流水号
    private String smsSendNo;//短信发送编号
    private String SMSType;//短信类型（0-开户 1-绑卡）
    private Date createTime;//发送时间

}
