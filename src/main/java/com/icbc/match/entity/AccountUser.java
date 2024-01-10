package com.icbc.match.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class AccountUser implements Serializable {

    @TableId
    private String userId;
    private String orgUserId;
    private String merchantId;
    private String mediumId;
    private String certType;
    private String certId;
    private String custName;

    private Date createTime;
    private Date successTime;
    private Date lstUpdateTime;

    private String marketNo;
    private String status;
    private String successFlag;
    private String origSerno;
    private String imageIdcardFace;
    private String imageIdcardEmblem;

    private String foreverFlag;
    private String signDate;
    private String validityPeriod;
    private String mobile;
    private String imageUserFace;
}
