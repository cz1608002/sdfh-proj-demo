package com.icbc.match.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.icbc.api.core.ApiClient;
import com.icbc.api.core.ApiException;
import com.icbc.api.core.ApiRequest;
import com.icbc.api.core.ApiResponse;
import com.icbc.match.config.ApiConstants;
import com.icbc.match.entity.CreditQryList;
import com.icbc.match.entity.KeyV;
import com.icbc.match.entity.SuppliersInfo;
import com.icbc.match.mapper.AccountDetailMapper;
import com.icbc.match.mapper.CreditQryMapper;
import com.icbc.match.mapper.SuppliersInfoMapper;
import com.icbc.match.mapper.ZyzrNoticeMapper;
import com.icbc.match.utils.SernoUtil;
import com.icbc.match.utils.Tool;
import com.icbc.match.utils.Tools;
import com.icbc.match.utils.YkHttpPost;
import lombok.extern.slf4j.Slf4j;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Configuration      //1.主要用于标记配置类，兼备Component的效果。
@EnableScheduling   // 2.开启定时任务
@Slf4j
public class LoanStatusQryScheduleService {


    @Autowired
    private CreditQryMapper creditQryMapper;
    @Autowired
    private SuppliersInfoMapper suppliersInfoMapper;

    @Autowired
    private SuppliersQryService suppliersQryService;

    @Autowired
    private ZyzrNoticeMapper zyzrNoticeMapper;

    @Autowired
    private AccountDetailMapper accountDetailMapper;

   // @Scheduled(cron = "0/5 * * * * ?")
    //或直接指定时间间隔，例如：5秒
   @Scheduled(fixedRate = 300000)
    private void configureLoStTasks() {


        String transcode = "RESULTQUERY";
       // String merid = ApiConstants.API_YKGYJ_MERID;
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        DateFormat dft = new SimpleDateFormat("HH:mm:ss");
        String trandate = "";
        String trantime = "";
        String transno = "";
        String billtype = "";
        String querystarttime = "";
        String queryendtime = "";
        String apiname = ApiConstants.API_YKGYJ_COM_ICBC_GYJ_ELOANSPLUS_RESULTQUERY;
        String version = "1.0.0.0";
        String charset = "UTF-8";
        //String priKey     = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCL7V997WUSu2Abv3/P9ipDxJEgvT2v53TgODVeoxITnIBky3404e36O0IvNizM0s2aMdjD0rzwinKRwcTFYXsXyQp3njUBQ7S3rs+4NK52M4f4d20kTa+8gfsnWZ8UYnfahUshA9uOgnDyZK7iANNrM9ix4txmfTtof5LfbJkDp3HFmMqGYfX92mE/tV2ckXLsaLgs4jDxRus0MXjtfzILmO7H9pGrlQjacEWm21YJGs3XHXUfuMh/utt9n6KqFa7fSrjUMZhgmHHY7G6f7hDNH12salfqKjX5cdgy7DH251M/rUXqiit+ADmaQJ3yCia2fyX/gyZ+QlILCcL9x+WfAgMBAAECggEANz7SC/L1i+7E5E+4U9A6IaVzMzzkJRY6M+idUa8KjQmmO6WIP7LP9ZL974vxJjE4kd+KWM5UYJgY4eliGhgBtINgiZfCD6y+piLzyiOqmeqAjZ4BTmsJrmCXW5Lr1u2FqpYj8nYGcn6Xv2v3DC4NSu8eaCVMpX5ePUTZJTNIOoN/Dykyb4xCcWyrudrjW047fPVULgLNerdVhjtxuk+FWiOihlYyc1NrJZPEDv2TckTiKeEyhsGjVHI8UnmED2dCo44FHE2AeDPHU6g3dF4OktBN04vLYVk1fGuEllk635oRJ19cgkWcrqRHdqg5MzixSanF8eE3UPecISwXaKDviQKBgQD8tm5aonMIeHoyrojEcult4QcdM2X3MsEvqyPLhrnjc/u6h/XdNRYxDfKArWzc5wotUwHOeEFZ3WQM0SXlKzCfJmljjPoq+no/AZLGGa6Q5g2BfNMoio4l6Uy0j5ucULk1rDEiS/bMpU9YtI+kV0ro6OgqDIfPmBOztt1IImF2iwKBgQCNv1nAuX4FUYqYRDHrRM8K5k+yRVm4p94cXA6eHjo+hLABaA4nEQlj0xxtwPOwEw2LuMdSoxyWUSfQfz45ZUHI8cCv6U8lFCfrPG3SEn8RPh2tQGdcCs4CBIUZK/VxASTCLG/NhyVpgmX2U+F5tr9QxpWc4kKDT2wd651fFAdDvQKBgGh2tSlsK6cdvk3DDjA+3DWapskwXP8RkQA2e4Z/e8oFQNK7ryuw7Sp7/Hmqtuyd6PwRaxbBaSpPtTMDJeoUr3WqeDg5p5QqlGwFJ3oSVbH6Fy0mv1br55TOWvHsx0OXruf79RebVTsFPsaJZTNnDkU3Oflf0qhC0iog9loCNE2/AoGAKoPvXsAETYIUqPEja+a6lxFXbCgi7iDjnOv7sm9slt3jkLhuLkeM4nUqvLy4GuLMGFF275Fe/LMg1wejWUHGxofEfL7k70EpbAQ3CGBl3n0cF6AOoGvZOH+0kEVAHAH0zYzYh/P2Q7xJ6uVj5sbqgUbfDyxKOOp0ilDmZeMy/p0CgYEA+KGMpO+S2EeF2k1+aj+xtPLZmXTotMYqIY/lsDFUm8e2U86m2eNT5VLQxU6EUj8+RIxhaziD8cA6mDx91OVeMXQN5URnCvJHvIA5NiIb21du9xEjV2o7DB1asOye6YkEW4pTKD8ugBwaHIyafZwleH9reUYiEE+ocZo/QLATEx0=";
        String priKey = ApiConstants.API_YKGJY_LOANPRIKEY;
        String b64Reqdata = "";
        String respStr = "";
        String apiurl = ApiConstants.API_YKGJY_URL;
        String appid = ApiConstants.API_YKGJY_AIPLOAN_APPID;
        String retCode = "";
        String retMsg = "";
        String BillStatus = "0";


        //查询accountCode as orderno

        ArrayList<CreditQryList> AccountCodelist = null;
        try {
            AccountCodelist = creditQryMapper.selectAccountCodeList();

            if (AccountCodelist.size() > 0) {
                for (int i = 0; i < AccountCodelist.size(); i++) {
                    trandate = df.format(new Date());
                    trantime = dft.format(new Date());
                    transno = SernoUtil.getSerno();
                    billtype = "0";
                    querystarttime = "2021-01-01 00:00:01";
                    queryendtime = trandate + " " + trantime;

                    String orderno = AccountCodelist.get(i).getAccountCode();
                    String merid = AccountCodelist.get(i).getMerid();
                    String reqdata = "<GYJ>" +
                            "<pub>" +
                            "<TransCode>" + transcode + "</TransCode>" +        //<!--和服务器时间间隔前后不超过15分钟,必输-->
                            "<MerId>" + merid + "</MerId>" +
                            "<TranDate>" + trandate + "</TranDate>" +
                            "<TranTime>" + trantime + "</TranTime>" +
                            "<TransNo>" + transno + "</TransNo>" +
                            "</pub>" +
                            "<in>" +
                            "<BillType>" + billtype + "</BillType>" +
                            "<QueryStartTime>" + querystarttime + "</QueryStartTime>" +
                            "<QueryEndTime>" + queryendtime + "</QueryEndTime>" +
                            "<OrderNo>" + orderno + "</OrderNo>" +
                            "<Page>1</Page>" +
                            "<PageCount>100</PageCount>" +
                            "</in>" +
                            "</GYJ>";

                    System.out.println(reqdata);

                    b64Reqdata = Tool.base64Encode(reqdata, charset);
		            b64Reqdata = b64Reqdata.replaceAll("\r\n", "");
		            b64Reqdata = b64Reqdata.replaceAll("\n", "");

		            String cerpath = Tools.getUrl();
		            System.out.println("path + 111111111"+cerpath);
                    //ApiClient ac = new ApiClient(priKey,  ApiConstants.YK_PUB_CERT_ROOT);
                    ApiClient ac = new ApiClient(priKey, cerpath);
                    //ApiClient ac = new ApiClient(priKey,"D:\\pubcerdir");
                    ApiRequest req = new ApiRequest(apiurl, apiname, appid);
                    req.setRequestField("version", version);
                    req.setRequestField("charset", charset);
                    req.setRequestField("merid", merid);
                    req.setRequestField("trancode", transcode);
                    req.setRequestField("reqdata", b64Reqdata);
                    System.out.println("请求信息：" + reqdata);

                    try {
                        ApiResponse ar = ac.execute(req);
                        if (ar.isCheckValid() && ar.getLong("ICBC_API_RETCODE") == 0) {
                            Map<Object, Object> res = ar.getMap("response");
                            System.out.println("解析res：" + res.toString());

                            if (res.get("status").equals("000")) {
                                Map<Object, Object> data = (Map<Object, Object>) res.get("data");
                                System.out.println("解析返回信息：" + data.toString());
                                retCode = data.get("retcode").toString();
                                retMsg = data.get("retmsg").toString();
                                String ansxml = data.get("ansxml").toString();
                                System.out.println("解析返回信息ansxml：" + ansxml);
                                String ans = Tool.base64Decode(ansxml, "UTF-8");
                                System.out.println("解析返回信息ans：" + ans);

                                try {
                                    SAXReader saxreader = new SAXReader();
                                    Document document = saxreader.read(new ByteArrayInputStream(Tool.base64Decode(ansxml, "UTF-8").getBytes("utf-8")));

                                    Element root = document.getRootElement();
                                    if ("200".equals(retCode)) {
                                        try {
                                            String totalCount = root.element("out").element("TotalCount").getTextTrim();
                                            System.out.println("解析返回信息totalCount：" + totalCount);
                                            if (totalCount == "0" || "0".equals(totalCount)) {
                                                BillStatus = "0";
                                                log.info("订单accountCode" + orderno + "未放款" + queryendtime);
                                                continue;

                                            }


                                            Element rds = root.element("out");
                                            List<Element> rdnodes = rds.elements("rd");

                                            for (Element rdnode : rdnodes) {

                                                String BillNo = rdnode.elementText("Billno");
                                                String ApplyNo = rdnode.elementText("ApplyNo");
                                                String CustomerId = rdnode.elementText("CustomerId");
                                                String BillType = rdnode.elementText("BillType");
                                                String BillAmount = rdnode.elementText("BillAmount");
                                                String OrderAmount = rdnode.elementText("OrderAmount");
                                                String LoanType = rdnode.elementText("LoanType");
                                                BillStatus = rdnode.elementText("BillStatus");
                                                String BillTime = rdnode.elementText("BillTime");


                                                System.out.println("申请编号为" + orderno + "的订单放款状态为" + BillStatus);
                                                //更新account_detail表放款状态
                                                try {
                                                    if (!"2".equals(BillStatus)) {
                                                        int num = accountDetailMapper.updateLoanStatus(BillStatus, orderno);
                                                        if (num > 0) {
                                                            log.info("订单" + orderno + "更新成功");
                                                            //发送第三方

                                                            String yknoticetUrl = ApiConstants.API_YK_HXQY_UPDATEZTSTATUS;
                                                            Map<String, String> paramsMap = new HashMap<String, String>();
                                                            appid = merid;
                                                            String key = ApiConstants.API_YK_KEY;
                                                            String noce_str = new Random().nextLong() + "";
                                                            String time_stamp = System.currentTimeMillis() / 1000 + "";

                                                            List<KeyV> kvs = new ArrayList<KeyV>();
                                                            kvs.add(new KeyV("appid", appid));
                                                            kvs.add(new KeyV("noce_str", noce_str));
                                                            kvs.add(new KeyV("time_stamp", time_stamp));

                                                            String str = Tools.join(kvs);
                                                            String ostr = str;
                                                            str = str + "&key=" + key;
                                                            String sign = "";
                                                            sign = Tools.getMd5(str);

                                                            String accountcode = orderno;
                                                            ArrayList<CreditQryList> CreditQryList = zyzrNoticeMapper.selectFinCode(accountcode);
                                                            String gysdksqbh = CreditQryList.get(0).getFinCode();
                                                            String htcode = CreditQryList.get(0).getContractNo();
                                                            String status = "";
                                                            if ("1".equals(BillStatus)) {
                                                                status = "1";
                                                            } else {
                                                                status = "3";
                                                            }
                                                            JSONObject jsbody = new JSONObject();
                                                            jsbody.put("gysdksqbh", gysdksqbh);
                                                            jsbody.put("htcode", htcode);
                                                            jsbody.put("status", status);

                                                            String body = jsbody.toJSONString();
                                                            paramsMap.put("appid", appid);
                                                            paramsMap.put("body", body);
                                                            paramsMap.put("noce_str", noce_str);
                                                            paramsMap.put("time_stamp", time_stamp);
                                                            paramsMap.put("sign", sign);

                                                            // 发送post请求并接收返回结果
                                                            String resultData = YkHttpPost.httpPostWithForm(yknoticetUrl, paramsMap);
                                                            System.out.println(resultData);
                                                            JSONObject jsresult = JSON.parseObject(resultData);
                                                            String code = jsresult.getString("code");
                                                            String msg = jsresult.getString("msg");

                                                            if ("10000".equals(code)){
                                                                log.info("兖矿接收放款信息成功");
                                                                msg = "兖矿接收放款信息成功";
                                                                num = accountDetailMapper.updateLoanBak1(msg, orderno);
                                                                continue;

                                                            } else {
                                                                log.info("兖矿接收放款信息失败");
                                                                //更新状态为待确认
                                                                BillStatus = "2";
                                                                num = accountDetailMapper.updateLoanStatus1(msg,BillStatus,orderno);
                                                                continue;
                                                            }


                                                        } else {
                                                            log.info("订单" + orderno + "更新失败");
                                                            continue;
                                                        }
                                                    }else{
                                                        log.info("订单" + orderno + "状态为未确认2无需更新");
                                                        continue;
                                                    }
                                                } catch (Exception e) {
                                                    e.printStackTrace();
                                                    log.info("订单" + orderno + "更新失败");
                                                    continue;
                                                }




                                            }
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                            log.info("订单" + orderno + "更新失败");
                                            continue;

                                        }


                                    } else {
                                        log.info("订单" + orderno + "更新失败");
                                        continue;
                                    }
                                } catch (DocumentException e) {
                                    e.printStackTrace();
                                    continue;
                                } catch (UnsupportedEncodingException e) {
                                    e.printStackTrace();
                                    continue;
                                }
                            } else {
                                log.info("没有新增已放款订单" + queryendtime);
                                continue;

                            }


                        } else {
                            log.info("订单" + orderno + "更新失败");
                            continue;
                        }
                    } catch (ApiException e) {
                        e.printStackTrace();
                        continue;
                    }


                }


            } else {
                log.info("没有需要放款的订单" + queryendtime);

            }
        } catch (Exception e) {
            e.printStackTrace();
            log.info("查询未放款订单出错" + e.getMessage());
        }
    }

}