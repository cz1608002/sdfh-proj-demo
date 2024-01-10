package com.icbc.match.entity;

import lombok.Data;

@Data
public class SettlementAccountBranchQueryOpen {


    private String corpNo;//合作方机构编号 true str(20) 20数字
    private String outServiceCode;//外部服务代码 true str(20) 示例: queryopenbook
    private String openCorpSerno;//合作方交易单号 true str(36) 示例: ABC123456789 (原开户或绑卡的交易单号)
    private String callType;//请求类型(H5、API) false - 默认API，H5时敏感数据不加密

}
