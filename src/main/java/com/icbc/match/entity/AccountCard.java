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
public class AccountCard implements Serializable {
    private static final long serialVersionUID = 1L;
    @TableId
    private String userId;//用户ID
    private String merchantId;//合作方商户ID
    private String mediumId;//二类户卡号
    private String bindMediumId;
    private String marketNo;//营销人编码
    private String marketTime;//营销时间
    private String origSerno;//开户流水号
    private String successFlag;//开户状态标志
    private Date createTime;//记录创建时间
    private Date openTimestamp;//开户成功时间戳
    private String openDate;//开户成功日期(YYYY-MM-DD)
    private String openTime;//开户成功时间(hh:mi:ss)
    private Date lstUpdateTime;//最后更新时间
    private String status;//状态
}
