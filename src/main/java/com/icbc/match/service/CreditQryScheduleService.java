package com.icbc.match.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.icbc.match.config.ApiConstants;
import com.icbc.match.entity.*;
import com.icbc.match.entry.Result;
import com.icbc.match.entry.YkgyjResult;
import com.icbc.match.mapper.CreditQryMapper;
import com.icbc.match.mapper.DebtorCodeMapper;
import com.icbc.match.mapper.SuppliersInfoMapper;
import com.icbc.match.utils.SernoUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import static com.icbc.match.utils.Tools.getTime;


@Configuration      //1.主要用于标记配置类，兼备Component的效果。
@EnableScheduling   // 2.开启定时任务
@Slf4j
public class CreditQryScheduleService {


    @Autowired
    private CreditQryMapper creditQryMapper;
    @Autowired
    private SuppliersInfoMapper suppliersInfoMapper;

    @Autowired
    private SuppliersQryService suppliersQryService;
    @Autowired
    private DebtorCodeMapper debtorCodeMapper;


 //   @Scheduled(cron = "0/5 * * * * ?")
    //或直接指定时间间隔，例如：5秒
    @Scheduled(fixedRate = 3600000)
    private void configureCreQryTasks() {
        String merid = "";
     //   String debtorcode = ApiConstants.API_YKGYJ_DEBTORCODE;
      //  String debtorname = "";
        String bankincode = "";
        String bankcode = "";
        String bankname = "";

        //查询未授信订单信息
        ArrayList<CreditQryList> CreditQrylist = creditQryMapper.selectCreditQryList();
        if (CreditQrylist.size() > 0) {

            for (int i = 0; i < CreditQrylist.size(); i++) {
                String debtorcode = CreditQrylist.get(i).getDebtorCode();
                String debtorname = CreditQrylist.get(i).getDebtorName();
                String creditcode = CreditQrylist.get(i).getCreditCode();
                String creditname = CreditQrylist.get(i).getCreditName();
                String customerid = CreditQrylist.get(i).getCustomerId();
                String productcode = CreditQrylist.get(i).getProduct_code();
                String accountcode = CreditQrylist.get(i).getAccountCode();
                String accountamt = CreditQrylist.get(i).getAccountAmt().toString();
                String stdate = CreditQrylist.get(i).getAccStartDate();
                String endate = CreditQrylist.get(i).getAccEndDate();
                String invoiceno = CreditQrylist.get(i).getInvoiceNo();
                String contraceno = CreditQrylist.get(i).getContractNo();
                String orderno = CreditQrylist.get(i).getContractNo();
                String dfy = CreditQrylist.get(i).getAccountYear();
                String note = CreditQrylist.get(i).getNote();
                String fincode = CreditQrylist.get(i).getFinCode();
                String finamt = CreditQrylist.get(i).getFinAmt().toString();
                String findays = String.valueOf(CreditQrylist.get(i).getFinDays());
                String acccount = CreditQrylist.get(i).getAccCount();


                String sqreturn_msg1 = "";
                //授信信息查询
                ArrayList<DebtorCode> DebtorCode = debtorCodeMapper.selectDebtorInfo(debtorcode);
                if (DebtorCode.size()>0){
                    merid = DebtorCode.get(0).getMerId();
                    bankincode = DebtorCode.get(0).getBankInCode();
                    bankcode = DebtorCode.get(0).getBankCode();
                    bankname = DebtorCode.get(0).getBankName();

                }else {
                    log.info("核心企业信息未录入"+debtorcode+debtorname);
                    continue;
                }


                JSONObject sqresult = suppliersQryService.suppliersQry(merid,debtorcode, creditcode);
                String sqreturn_code = sqresult.getString("return_code");
                if ("0".equals(sqreturn_code)) {



                        //记录授信信息设置表suppliers_info status为1已授信
                        JSONObject sqdata = sqresult.getJSONObject("data");
                        String sqcreditflag = sqdata.getString("creditflag");
                        if ("1".equals(sqcreditflag)) {
                            try {

                            String sqcustomerid = sqdata.getString("customerId");
                            String sqbankcode = sqdata.getString("bankCode");
                            String sqbankname = sqdata.getString("bankName");
                            String sqfinproductcode = sqdata.getString("finProductCode");
                            String sqmakecreditdate = sqdata.getString("make_credit_date");

                            String sqbelongto = sqdata.getString("belongTo");
                            String sqinstname = sqdata.getString("instname");
                            String sqaccno = sqdata.getString("accno");
                            String sqaccname = sqdata.getString("accname");
                            String sqopenbank = sqdata.getString("openbank");
                            String sqcreditamt = sqdata.getString("creditAmt");
                            String sqmaxperiod = sqdata.getString("maxPeriod");
                            String status = "1";
                            String tbstatus = "1";
                            SuppliersInfo suppliersinfo = new SuppliersInfo();
                            suppliersinfo.setStatus(status);
                            suppliersinfo.setCustomerId(sqcustomerid);
                            suppliersinfo.setBankCode(sqbankcode);
                            suppliersinfo.setBankName(sqbankname);
                            suppliersinfo.setFinProductCode(sqfinproductcode);
                            suppliersinfo.setMake_credit_date(sqmakecreditdate);
                            suppliersinfo.setCreditflag(sqcreditflag);
                            suppliersinfo.setBelongTo(sqbelongto);
                            suppliersinfo.setInstname(sqinstname);
                            suppliersinfo.setAccno(sqaccno);
                            suppliersinfo.setAccname(sqaccname);
                            suppliersinfo.setOpenbank(sqopenbank);
                            suppliersinfo.setCreditAmt(sqcreditamt);
                            suppliersinfo.setMaxPeriod(sqmaxperiod);
                            suppliersinfo.setApplyCode(creditcode);

                            suppliersInfoMapper.saveSuppliersApplyStatus(suppliersinfo);

                            //发起融资申请

                            String fseqno = SernoUtil.getSerno();
                            //  JSONObject param1 = new JSONObject();
                            JSONObject fdata = new JSONObject();
                            JSONObject fdataobj = new JSONObject();
                            //   JSONObject param3 = new JSONObject();
                            JSONObject freqdata = new JSONObject();
                            freqdata.put("merid", merid);
                            freqdata.put("timestamp", getTime());
                            freqdata.put("fseqno", fseqno);
                            freqdata.put("field1", "");
                            freqdata.put("field2", "");
                            freqdata.put("field3", "");


                            FinancingList flist = new FinancingList();
                            flist.setDebtor_code(debtorcode);
                            flist.setDebtor_name(debtorname);
                            flist.setCredit_code(creditcode);
                            flist.setCredit_name(creditname);
                            flist.setCustomer_id(customerid);
                            flist.setBank_in_code(bankincode);
                            flist.setBank_code(bankcode);
                            flist.setBank_name(bankname);
                            flist.setProduct_code(productcode);
                            flist.setAccountCode(accountcode);
                            flist.setAccountAmt(accountamt); //???应收账款金额  授信金额   账款金额
                            flist.setAccStartDate(stdate.toString());
                            flist.setAccEndDate(endate.toString());
                            flist.setInvoiceNo(invoiceno);
                            flist.setContractNo(contraceno);
                            flist.setOrderNo(orderno);
                            flist.setAccountYear(dfy.toString());
                            flist.setNote(note);

                            JSONArray flistArray = new JSONArray();
                            flistArray.add(flist);
                            fdata.put("accountList", flistArray);
                            fdata.put("finCode", fincode);
                            fdata.put("debtorCode", debtorcode);
                            fdata.put("debtorName", debtorname);
                            fdata.put("bankInCode", bankincode);
                            fdata.put("creditCode", creditcode);
                            fdata.put("creditName", creditname);
                            fdata.put("customerId", customerid);
                            fdata.put("bankCode", bankcode);
                            fdata.put("bankName", bankname);
                            fdata.put("finProductCode", productcode);
                            fdata.put("finAmt", finamt);//意向金额，账款金额？
                            fdata.put("finDays", findays);
                            fdata.put("accCount", acccount);

                            freqdata.put("data", fdata);
                            log.info("reqdata{}" + freqdata.toString());
                            String fresult = null;
                            fresult = new RequestBuilder(ApiConstants.API_YKGJY_URL, ApiConstants.API_YKGJY_AIPGW_APPID, ApiConstants.API_YKGJY_PRIKEY)
                                    .apiname(ApiConstants.API_YKGYJ_COM_ICBC_GYJ_YSZK_FINANCINGAPPLY)
                                    .data(freqdata.toJSONString())
                                    .build()
                                    .query();

                            System.out.println(fresult);

                            JSONObject obj = JSON.parseObject(fresult);
                            String freturn_code = obj.getString("return_code");
                            if ("0".equals(freturn_code)) {
                                log.info("订单" + accountcode + "融资同步成功");
                            } else {
                                String freturn_msg = obj.getString("return_msg");
                                log.info("订单" + accountcode + "融资同步失败" + freturn_msg);
                            }
                        } catch(IllegalAccessException e){
                            e.printStackTrace();
                            e.getMessage();
                            log.info("订单" + accountcode + "融资同步失败" + e.getMessage());

                        } catch(IOException e){
                            e.printStackTrace();
                            log.info("订单" + accountcode + "融资同步失败" + e.getMessage());
                        }
                    }else{
                            log.info("订单" + accountcode + "无授信信息" + Calendar.getInstance().getTime());
                        }
                } else {
                    //无授信信息
                    log.info("订单" + accountcode + "无授信信息" + Calendar.getInstance().getTime());

                }
            }

        } else {
            log.info("今天无待查询授信任务: " + Calendar.getInstance().getTime());
        }


    }
}
