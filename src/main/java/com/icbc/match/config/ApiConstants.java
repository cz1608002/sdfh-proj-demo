package com.icbc.match.config;

import com.icbc.match.entity.CreditQryList;
import com.icbc.match.entity.DebtorCode;
import com.icbc.match.mapper.DebtorCodeMapper;
import com.icbc.match.mapper.ZyzrNoticeMapper;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class ApiConstants {

    //API平台接口默认编码格式
    public static final String CHAR_SET = "UTF-8";
    public static final String PROJECT_ID = "icbc-account";
    public static final String CERT_NO = "icbc-account";
    public static final String CORP_NO = "10000000000000130018";
    public static final String ACC_DATE = "2021-01-01";
    public static final String FHJS025 = "FHJS025";
    public static final String CORP_MEDIUM_ID = "FHJS025WX001";


    public static final String API_SETTLEMENT_ACCOUNT_BRANCH_OPEN_V2 = "/settlement/account/branch/open/V2";//提供分行通过API接口开立个人II、III类结算账户功能
    public static final String API_SETTLEMENT_ACCOUNT_BRANCH_QUERY_APPLY_V1 = "/settlement/account/branch/query/apply/V1";//查询结算类申请结果
    public static final String API_SETTLEMENT_ACCOUNT_BRANCH_SCODE_SEND_V1 = "/settlement/account/branch/scode/send/V1";//结算账户验证码发送（开户+绑卡）
    public static final String API_SETTLEMENT_ACCOUNT_BRANCH_QUERY_OPEN_V1 = "/settlement/account/branch/query/open/V1";//查询管理类申请结果
    public static final String API_SETTLEMENT_ACCOUNT_BRANCH_SCODE_VERIFY_V1 = "/settlement/account/branch/scode/verify/V1";//结算账户短信验证码校验（开户+绑卡）
    public static final String API_SETTLEMENT_ACCOUNT_BRANCH_RECHARGE_V1 = "/settlement/account/branch/recharge/V1";//结算账户充值
    public static final String API_SETTLEMENT_ACCOUNT_BRANCH_DETAIL_QUERY_V1 = "/settlement/account/branch/detail/query/V1";//结算账户交易明细查询
    public static final String API_SETTLEMENT_ACCOUNT_BRANCH_BALANCE_QUERY_V1 = "/settlement/account/branch/balance/query/V1";//结算账户余额查询
    public static final String API_SETTLEMENT_ACCOUNT_BRANCH_WITHDRAW_V1 = "/settlement/account/branch/withdraw/V1";//结算账户提现
    public static final String API_SETTLEMENT_ACCOUNT_BRANCH_BINDING_V1 = "/settlement/account/branch/binding/V1";//结算账户绑卡维护
    public static final String API_SETTLEMENT_ACCOUNT_BRANCH_UNBINDING_V1 = "/settlement/account/branch/unbinding/V1";//结算账户解绑维护
    public static final String API_SETTLEMENT_ACCOUNT_BRANCH_BINDING_QUERY_V1 = "/settlement/account/branch/binding/query/V1";//结算账户绑卡信息查询
    public static final String API_SETTLEMENT_ACCOUNT_BRANCH_ORIENTEDTRANSFER_V1 = "/settlement/account/branch/orientedtransfer/V1";//结算账户定向转账
    public static final String API_SETTLEMENT_ACCOUNT_BRANCH_REALTIMEIMG_UPLOAD_V1 = "/settlement/account/branch/realtimeimg/upload/V1";//实时影像上传
    public static final String API_SETTLEMENT_ACCOUNT_BRANCH_CLOSEACCEPT_V1 = "/settlement/account/branch/closeaccept/v1";//电子账户二类卡状态查询

//    //兖矿工银聚参数test
//    public static final String API_YKGYJ_MERID = "gyj.yankuang";
//    public static final String API_YKGYJ_DEBTORCODE = "gyj.yankuang";
//    public static final String API_YKGYJ_DEBTORNAME = "山东分行";
//    public static final String API_YKGYJ_BANKINCODE= "160800001";
//    public static final String API_YKGYJ_BANKCODE = "003";
//    public static final String API_YKGYJ_BANKNAME = "中国工商银行股份有限公司杭州分行";
//    public static final String API_YKGJY_URL = "http://gyjapi1.dccnet.com.cn/api";
//    public static final String API_YKGJY_AIPGW_APPID = "gyj.yankuang";
//    public static final String API_YKGJY_AIPLOAN_APPID = "rongzi";
//    public static final String API_YKGJY_PRIKEY = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCL7V997WUSu2Abv3/P9ipDxJEgvT2v53TgODVeoxITnIBky3404e36O0IvNizM0s2aMdjD0rzwinKRwcTFYXsXyQp3njUBQ7S3rs+4NK52M4f4d20kTa+8gfsnWZ8UYnfahUshA9uOgnDyZK7iANNrM9ix4txmfTtof5LfbJkDp3HFmMqGYfX92mE/tV2ckXLsaLgs4jDxRus0MXjtfzILmO7H9pGrlQjacEWm21YJGs3XHXUfuMh/utt9n6KqFa7fSrjUMZhgmHHY7G6f7hDNH12salfqKjX5cdgy7DH251M/rUXqiit+ADmaQJ3yCia2fyX/gyZ+QlILCcL9x+WfAgMBAAECggEANz7SC/L1i+7E5E+4U9A6IaVzMzzkJRY6M+idUa8KjQmmO6WIP7LP9ZL974vxJjE4kd+KWM5UYJgY4eliGhgBtINgiZfCD6y+piLzyiOqmeqAjZ4BTmsJrmCXW5Lr1u2FqpYj8nYGcn6Xv2v3DC4NSu8eaCVMpX5ePUTZJTNIOoN/Dykyb4xCcWyrudrjW047fPVULgLNerdVhjtxuk+FWiOihlYyc1NrJZPEDv2TckTiKeEyhsGjVHI8UnmED2dCo44FHE2AeDPHU6g3dF4OktBN04vLYVk1fGuEllk635oRJ19cgkWcrqRHdqg5MzixSanF8eE3UPecISwXaKDviQKBgQD8tm5aonMIeHoyrojEcult4QcdM2X3MsEvqyPLhrnjc/u6h/XdNRYxDfKArWzc5wotUwHOeEFZ3WQM0SXlKzCfJmljjPoq+no/AZLGGa6Q5g2BfNMoio4l6Uy0j5ucULk1rDEiS/bMpU9YtI+kV0ro6OgqDIfPmBOztt1IImF2iwKBgQCNv1nAuX4FUYqYRDHrRM8K5k+yRVm4p94cXA6eHjo+hLABaA4nEQlj0xxtwPOwEw2LuMdSoxyWUSfQfz45ZUHI8cCv6U8lFCfrPG3SEn8RPh2tQGdcCs4CBIUZK/VxASTCLG/NhyVpgmX2U+F5tr9QxpWc4kKDT2wd651fFAdDvQKBgGh2tSlsK6cdvk3DDjA+3DWapskwXP8RkQA2e4Z/e8oFQNK7ryuw7Sp7/Hmqtuyd6PwRaxbBaSpPtTMDJeoUr3WqeDg5p5QqlGwFJ3oSVbH6Fy0mv1br55TOWvHsx0OXruf79RebVTsFPsaJZTNnDkU3Oflf0qhC0iog9loCNE2/AoGAKoPvXsAETYIUqPEja+a6lxFXbCgi7iDjnOv7sm9slt3jkLhuLkeM4nUqvLy4GuLMGFF275Fe/LMg1wejWUHGxofEfL7k70EpbAQ3CGBl3n0cF6AOoGvZOH+0kEVAHAH0zYzYh/P2Q7xJ6uVj5sbqgUbfDyxKOOp0ilDmZeMy/p0CgYEA+KGMpO+S2EeF2k1+aj+xtPLZmXTotMYqIY/lsDFUm8e2U86m2eNT5VLQxU6EUj8+RIxhaziD8cA6mDx91OVeMXQN5URnCvJHvIA5NiIb21du9xEjV2o7DB1asOye6YkEW4pTKD8ugBwaHIyafZwleH9reUYiEE+ocZo/QLATEx0=";
//    public static final String API_YKGJY_LOANPRIKEY = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBALPNbn/eUbWWFCfXgPsUy9Y+zeLgZwLNFYtHZRHNpCt0P1FkdgrdR4c4NmcWb7uD0x3IZFDLy3RDVESdRCnewUR1sDBbJkGdBYeTxyrdJ+P0e9pS1aPF74j/jgmdEKFBmxtFIpOvosqOWgYVqV5uCen7C9c3Hxnlw+kraWW1jbKPAgMBAAECgYAzbtFsHHFtKzgqyXeo2yMP7zk/7AcrgvTluY+MzzFmCMPQCZfGtdOvN4JAbOebUTND8r3XauSRJm1lj0MfYwwPiK/RKERBBQXI50s1R0IpwqKIExNVf16s1DAFz64yFNn8s6Fpw4Dou7muOgZeug26tugbN1IOm97ayps8aogzGQJBAPFB6GHq+ZlGVVciiVMQ9Xltf+w6ra1vh+WW4RtIyB9VJYHywLqTJt7CEP1+5O8bbUvxPzRkM9K/xDSJQuv166UCQQC+yihYoxYuj0Cybx7yAIXJXdGM8cZ4JBKYlNon2GbkI8XPOrq7x9le6T/Vxz5LGkiQ2/7GuVfMZa09eWQ2158jAkAfq3Iw4GKRf00Wmh8Cu3gtz3T6cp135esl9U9pgH1ct5Wr4EzycPyqQzltvWmaBbSsQmf0na673JaJ+vbA+NBNAkAE6r7ZcfKLZqO7NYPtprfL9cAFyuFbjUpxZjytFDWqoYVijtfrKP5gJNDBWK4xT08ZdKH/Sx2JJgGt+OftRedTAkEAvYLEHSg5YTZAFUpqaY9zMjhyoJ9LCENnmJDDSvQZikk9Pjpmt3+5YGeodzxIYVI7hDcrKtfTx3/5cd9i5rQHfQ==";
//    //public static final String YK_PUB_CERT_ROOT = "/opt/yktest/pubcerdir";
//    public static final String YK_PUB_CERT_ROOT = "/";
//
//
//    public static final String API_YKGYJ_COM_ICBC_GYJ_YSZK_SUPPLIERQRY = "com.icbc.gyj.yszk.supplierqry";
//    public static final String API_YKGYJ_COM_ICBC_GYJ_YSZK_SUPPLIERINFO = "com.icbc.gyj.yszk.supplierinfo";
//    public static final String API_YKGYJ_COM_ICBC_GYJ_YSZK_FINANCINGAPPLY = "com.icbc.gyj.yszk.financingapply";
//    public static final String API_YKGYJ_COM_ICBC_GYJ_YSZK_PLEDGERESULT = "com.icbc.gyj.yszk.pledgeresult";
//    public static final String API_YKGYJ_COM_ICBC_GYJ_ELOANSPLUS_RESULTQUERY = "com.icbc.gyj.eloansplus.resultquery";
//    public static final String API_YKGYJ_COM_ICBC_GYJ_ELOANSPLUS_LOANLIST = "com.icbc.gyj.eloansplus.loanlist";
//
//    public static final String API_GYJ_TEST_2 = "iyiu.cc.cc";
//
//    //兖矿参数test
//    public static final String API_YK_HXQY_UPDATECONTRACTSIGN = "http://60.211.234.248:80/hxqy/hxqydk/api/updateContractSign";
//    public static final String API_YK_HXQY_UPDATEZTSTATUS = "http://60.211.234.248:80/hxqy/hxqydk/api/updateZyStatus";
//    public static final String API_YK_HXQY_UPDATEFINISH = "http://60.211.234.248:80/hxqy/hxqydk/api/updateFinish";
//
//
//    public static final String API_YK_KEY = "123456";



    //兖矿工银聚参数生产
    //兖矿国宏
//    public static final String API_YKGYJ_MERID = "91370000773198848H";
//   // public static final String API_YKGYJ_DEBTORCODE = "77319884-8";
//    public static final String API_YKGYJ_DEBTORCODE = "91370000773198848H";
//    public static final String API_YKGYJ_DEBTORNAME = "兖矿国宏化工有限责任公司";
    //兖矿东华
/*        public static final String API_YKGYJ_MERID = "91370000773198848H";
   // public static final String API_YKGYJ_DEBTORCODE = "77319884-8";
    public static final String API_YKGYJ_DEBTORCODE = "91370000773198848H";
    public static final String API_YKGYJ_DEBTORNAME = "兖矿东华建设有限公司";
    public static final String API_YKGYJ_BANKINCODE= "0160800030";
    public static final String API_YKGYJ_BANKCODE = "26715711-1";
    public static final String API_YKGYJ_BANKNAME = "中国工商银行股份有限公司邹城支行";*/
    //public static final String API_YKGJY_URL = "http://76.106.245.116/api";//专线
     public static final String API_YKGJY_URL = "https://gyjapi.icbc.com.cn/api";//互联网
    //  public static final String API_YKGJY_URL = "http://gyjapi1.dccnet.com.cn/api";
    public static final String API_YKGJY_AIPGW_APPID = "gyj.yankuang";//生产唯一
    public static final String API_YKGJY_AIPLOAN_APPID = "gyj.yankuang";//生产唯一
    public static final String API_YKGJY_PRIKEY = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAO64XIQSuT2mTm56PJlSROm+g0P+ieRCuhZPIzUjHHE+ndndlk99wJDsz3NLKdr3w7dOkHB6w9AvX4HKA7nKv9+7/lrzPouNuoypCjRSw5EBIeBDFDRtiwViqQbUq6lIw5DwQgBJu3gO6p4yPOpudv4NCUv6gbN5oOcBj8vNWPShAgMBAAECgYBJt6jqKBYIEak6uIiqGBuryuciQiI22Nw6M2n96L/JwLpiIuld1sl2d4qAYf+k3MoVey9jbKW3TknB/K4124RwbBFo59V05jLVEyEk86gP8ZzuBWIHc9LZiPlDhCCmgZO7hHTXAKWGvGv0BjLjz9YHq44C2y4PW+idtO/iQVXbOQJBAP5aI2rQdyr/inNFRGcWTgc0OEFALHrCY++VNd0wULbHODS1MXkBRSb1C6jacTZ/db43ujwMye+qCyA8dA7o+dcCQQDwREvbiE4sPW8SZgoyMoqggsXbHsdjKGUwjm8ePNl9pHG5v9FAIx8Sh1bawoIW4L6N16LHx8jnV88vjhjcUGZHAkEA1bS2fVCaT65VjXRtfI/pD3R4r5pTxPD/NpWPpECsEGZieyaiYqEGkSzilwSyiq+dJi18dGfqY0vj/TnN6ZkmEQJAUUn6FEtYj3U1iBcH6hXZexbjlBAky3KWUchO5f8WYoDpjTvNsxJu59jsVNUsKEtfjSPgyhDY8xRtX5yG4rs87wJBALztvJA4Q553o9sXnCdrCacCjDQIr9DRuersKtbuGAMyem0sEkUmaj6YwQfSDlrCz+vSZ3NUFznyLUL5nYhyut8=";
    public static final String API_YKGJY_LOANPRIKEY = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAO64XIQSuT2mTm56PJlSROm+g0P+ieRCuhZPIzUjHHE+ndndlk99wJDsz3NLKdr3w7dOkHB6w9AvX4HKA7nKv9+7/lrzPouNuoypCjRSw5EBIeBDFDRtiwViqQbUq6lIw5DwQgBJu3gO6p4yPOpudv4NCUv6gbN5oOcBj8vNWPShAgMBAAECgYBJt6jqKBYIEak6uIiqGBuryuciQiI22Nw6M2n96L/JwLpiIuld1sl2d4qAYf+k3MoVey9jbKW3TknB/K4124RwbBFo59V05jLVEyEk86gP8ZzuBWIHc9LZiPlDhCCmgZO7hHTXAKWGvGv0BjLjz9YHq44C2y4PW+idtO/iQVXbOQJBAP5aI2rQdyr/inNFRGcWTgc0OEFALHrCY++VNd0wULbHODS1MXkBRSb1C6jacTZ/db43ujwMye+qCyA8dA7o+dcCQQDwREvbiE4sPW8SZgoyMoqggsXbHsdjKGUwjm8ePNl9pHG5v9FAIx8Sh1bawoIW4L6N16LHx8jnV88vjhjcUGZHAkEA1bS2fVCaT65VjXRtfI/pD3R4r5pTxPD/NpWPpECsEGZieyaiYqEGkSzilwSyiq+dJi18dGfqY0vj/TnN6ZkmEQJAUUn6FEtYj3U1iBcH6hXZexbjlBAky3KWUchO5f8WYoDpjTvNsxJu59jsVNUsKEtfjSPgyhDY8xRtX5yG4rs87wJBALztvJA4Q553o9sXnCdrCacCjDQIr9DRuersKtbuGAMyem0sEkUmaj6YwQfSDlrCz+vSZ3NUFznyLUL5nYhyut8=";
    //public static final String YK_PUB_CERT_ROOT = "/opt/yktest/pubcerdir";
    public static final String YK_PUB_CERT_ROOT = "/";


    public static final String API_YKGYJ_COM_ICBC_GYJ_YSZK_SUPPLIERQRY = "com.icbc.gyj.yszk.supplierqry";
    public static final String API_YKGYJ_COM_ICBC_GYJ_YSZK_SUPPLIERINFO = "com.icbc.gyj.yszk.supplierinfo";
    public static final String API_YKGYJ_COM_ICBC_GYJ_YSZK_FINANCINGAPPLY = "com.icbc.gyj.yszk.financingapply";
    public static final String API_YKGYJ_COM_ICBC_GYJ_YSZK_PLEDGERESULT = "com.icbc.gyj.yszk.pledgeresult";
    public static final String API_YKGYJ_COM_ICBC_GYJ_ELOANSPLUS_RESULTQUERY = "com.icbc.gyj.eloansplus.resultquery";
    public static final String API_YKGYJ_COM_ICBC_GYJ_ELOANSPLUS_LOANLIST = "com.icbc.gyj.eloansplus.loanlist";

    public static final String API_GYJ_TEST_2 = "iyiu.cc.cc";

    //兖矿参数生产
 /*   public static final String API_YK_HXQY_UPDATECONTRACTSIGN = "https://sdrxt.ykjt.cn/hxqy/hxqydk/api/updateContractSign";
    public static final String API_YK_HXQY_UPDATEZTSTATUS = "https://sdrxt.ykjt.cn/hxqy/hxqydk/api/updateZyStatus";
    public static final String API_YK_HXQY_UPDATEFINISH = "https://sdrxt.ykjt.cn/hxqy/hxqydk/api/updateFinish";
*/

    public static final String API_YK_HXQY_UPDATECONTRACTSIGN = "https://sdrxt.shandong-energy.com/hxqy/hxqydk/api/updateContractSign";//质押转让通知
    public static final String API_YK_HXQY_UPDATEZTSTATUS = "https://sdrxt.shandong-energy.com/hxqy/hxqydk/api/updateZyStatus";//放款通知
    public static final String API_YK_HXQY_UPDATEFINISH = "https://sdrxt.shandong-energy.com/hxqy/hxqydk/api/updateFinish";//还款通知


    public static final String API_YK_KEY = "123456";
    /**
     * 工银e钱包服务
     */
    public static final String UI_EACCOUNT_MANAGE_V1 = "/eaccount/manage/V1";

    /**
     * 合作方E支付注册界面服务
     */
    public static final String UI_B2C_EPAY_PARTNER_REGISTER_SIGN_V1 = "/b2c/epay/partner/register/sign/V1";

    /**
     * 结算账户退货
     */
    public static final String API_SETTLEMENT_ACCOUNT_V1_REFUND = "/settlement/account/V1/refund";


    public static final String API_EPAYMENT_PROJECT_V2 = "/epayment/project/V2";//工银e缴费缴费大厅
    public static final String API_CERT_PERSONAL_PROOF_PROOFCHECK_V1 = "/cert/personal/proof/proofcheck/V1";//身份证图像识别服务
    public static final String API_CERT_PERSONAL_ENTITY_ENTITYAUTH_V1 = "/cert/personal/entity/entityauth/V1";//身份实体认证服务

    public static final String API_CSI_ACCOUNT_V1_RESERVE = "/csi/account/V1/reserve";//保留和解保留


    public static final String API_BUSINESSOP_ECOUP_ECOUPACTRULEADD_V1 = "/businessop/ecoup/ecoupactruleadd/V1";//保留和解保留


    public static final String API_SETTLEMENT_ACCOUNT_OPEN_V2 = "/settlement/account/open/V2";//提供分行通过API接口开立个人II、III类结算账户功能
    public static final String API_SETTLEMENT_ACCOUNT_SCODE_V1_SEND = "/settlement/account/scode/V1/send";//提供合作方对我行个人结算账户开户受理后进行手机校验时，重新发送短信验证码的功能
    public static final String API_SETTLEMENT_ACCOUNT_SCODE_V1_VERIFY = "/settlement/account/scode/V1/verify";//结算账户短信验证码校验（开户+绑卡）
    public static final String API_SETTLEMENT_ACCOUNT_OPEN_V1_QUERY = "/settlement/account/open/V1/query";//结算账户开户或绑卡申请查询
    public static final String API_SETTLEMENT_ACCOUNT_INFO_QUERY_V1 = "/settlement/account/info/query/V1";//
    public static final String API_SETTLEMENT_ACCOUNT_DETAIL_V1_QUERY = "/settlement/account/detail/V1/query";//
    public static final String API_SETTLEMENT_ACCOUNT_BALANCE_V1_QUERY = "/settlement/account/balance/V1/query";//
    public static final String API_SETTLEMENT_ACCOUNT_V1_RECHARGE = "/settlement/account/V1/recharge";//
    public static final String API_SETTLEMENT_ACCOUNT_APPLY_V1_QUERY = "/settlement/account/apply/V1/query";//
    public static final String API_SETTLEMENT_ACCOUNT_V1_WITHDRAW = "/settlement/account/V1/withdraw";//
    public static final String API_SETTLEMENT_ACCOUNT_V1_BINDING = "/settlement/account/V1/binding";//
    public static final String API_SETTLEMENT_ACCOUNT_V1_UNBINDING = "/settlement/account/V1/unbinding";//
    public static final String API_SETTLEMENT_ACCOUNT_BINDING_V1_QUERY = "/settlement/account/binding/V1/query";//
    public static final String API_SETTLEMENT_ACCOUNT_ORIENTEDTRANSFER_V1 = "/settlement/account/orientedtransfer/V1";//
    public static final String API_SETTLEMENT_ACCOUNT_V1_CLOSEACCEPT = "/settlement/account/V1/closeaccept";//
    public static final String API_SETTLEMENT_ACCOUNT_HOLD_V1 = "/settlement/account/hold/V1";//结算账户保留
    public static final String API_SETTLEMENT_ACCOUNT_UNHOLD_V1 = "/settlement/account/unhold/V1";//结算账户解除保留
    public static final String API_SETTLEMENT_ACCOUNT_UNHOLDTRANSFER_V1 = "/settlement/account/unholdtransfer/V1";//解保留转账
    public static final String API_SETTLEMENT_ACCOUNT_CANCEL_PRECHECK_V1 = "/settlement/account/cancel/precheck/V1";//销户预检查
    public static final String API_SETTLEMENT_ACCOUNT_STATUS_QUERY_V1 = "/settlement/account/status/query/V1";//电子账户二类卡状态查询

    public static final String API_DEPOSIT_FIXED_REDEEM_V1 = "/deposit/fixed/redeem/V1";//定期产品赎回服务
    public static final String API_DEPOSIT_FIXED_QUERY_V1 = "/deposit/fixed/query/V1";//定期产品账户余额查询服务
    public static final String API_DEPOSIT_FIXED_PURCHASE_V1 = "/deposit/fixed/purchase/V1";//定期产品购买服务
    public static final String API_DEPOSIT_FIXED_TOTALQUERY_V1 = "/deposit/fixed/totalquery/V1";//定期产品账户汇总余额查询服务
    public static final String API_DEPOSIT_FIXED_PRODQUERY_V1 = "/deposit/fixed/prodquery/V1";//定期产品详情查询服务
    public static final String API_DEPOSIT_ACCOUNT_CORPREQUESTQRY_V1 = "/deposit/account/corprequestqry/V1";//定期产品详情查询服务
    public static final String API_DEPOSIT_FIXED_ACCOUNTLOCK_V1 = "/deposit/fixed/accountlock/V1";//定期产品详情查询服务

    public static final String API_B2C_PASSFREE_PAYMENT_QUERY_V1 = "/b2c/passfree/payment/query/V1";
    public static final String API_B2C_PASSFREE_PAYMENT_SIGN_V1 = "/b2c/passfree/payment/sign/V1";
    public static final String API_B2C_PASSFREE_PAYMENT_TERMINATE_V1 = "/b2c/passfree/payment/terminate/V1";
    public static final String API_B2C_PASSFREE_PAYMENT_MESSAGESEND_V1 = "/b2c/passfree/payment/messagesend/V1";
    public static final String API_B2C_PASSFREE_PAYMENT_PAY_V3 = "/b2c/passfree/payment/pay/V3";
    public static final String API_B2C_ORDERQRY_SEARCH_V1 = "/b2c/orderqry/search/V1";


//    public String getUrl() {
//        String path = "/static";
//
//        File f = new File("/static/pub.cer");
//        InputStream in = this.getClass().getClassLoader().getResourceAsStream("static/pub.cer");
//
//        try {
//            FileUtils.copyInputStreamToFile(in,f);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        return path;
//
//    }


}
