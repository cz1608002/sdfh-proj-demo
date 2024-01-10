package com.icbc.match.service;

import com.alibaba.druid.support.json.JSONUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.JsonObject;
import com.icbc.match.config.ApiConstants;
import com.icbc.match.entity.*;
import com.icbc.match.entry.Result;
import com.icbc.match.entry.YkgyjResult;
import com.icbc.match.mapper.*;
import com.icbc.match.service.SuppliersQryService;
import com.icbc.match.utils.SernoUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import static com.icbc.match.utils.Tools.getTime;
@Service
@Slf4j
public class FinApplyService {


    @Autowired
    private ZyzrNoticeMapper zyzrNoticeMapper;
    @Autowired
    private AccountDetailMapper accountDetailMapper;
    @Autowired
    private SuppliersInfoMapper suppliersInfoMapper;
    @Autowired
    private FinApplyMapper finApplyMapper;
    @Autowired
    private SuppliersQryService suppliersQryService;

    @Autowired
    private DebtorCodeMapper debtorCodeMapper;


    public JSONObject finApplyRecord(Body supply_info) {


        //String supplyinfo = (String)supply_info.get("apigw_rspdata");
       //JSONObject jssupplyinfo = new JSONObject(supply_info);
        String fseqno = "";
        String merid = "";
        //String debtorcode = "";
        String debtorname = "";
        String bankincode = "";
        String bankcode = "";
        String bankname = "";
     //   JSONObject gys = jssupplyinfo.getJSONObject("gys");
        String debtorcode = supply_info.getCorpnamesource();
        ArrayList<DebtorCode> DebtorCode = debtorCodeMapper.selectDebtorInfo(debtorcode);
        if (DebtorCode.size()>0){
            merid = DebtorCode.get(0).getMerId();
            debtorname = DebtorCode.get(0).getDebtorName();
            bankincode = DebtorCode.get(0).getBankInCode();
            bankcode = DebtorCode.get(0).getBankCode();
            bankname = DebtorCode.get(0).getBankName();

        }else {
            return YkgyjResult.failed("9999", "核心企业信息未录入");
        }
        String fincode = supply_info.getGysdksqbh();
        Gys gys = supply_info.getGys();
        String creditcode = gys.getGysbm();
        String creditname = gys.getGysmc();
        String applycorpname = gys.getFddbrxm();
        String applycorpidnum = gys.getFddbrszzhm();
        String contactname = gys.getLxrxm();
        String customerid = creditcode;
        //   ....

        Yzzkxx yzzkxx = supply_info.getYzzkxx();
        String accountcode = yzzkxx.getYzzkbm();
        BigDecimal finamt = new BigDecimal(yzzkxx.getYzzkje());

        DateFormat df=new SimpleDateFormat("yyyyMMdd");


        String stdate = yzzkxx.getKsrq();
        String endate = yzzkxx.getDqrq();
        int findays = 0;
        try {
            Calendar  st  =  Calendar.getInstance();
            st.setTime(df.parse(stdate));

            Calendar  en  =  Calendar.getInstance();
            en.setTime(df.parse(endate));

            int stYear = st.get(Calendar.YEAR);
            int stMonth = st.get(Calendar.MONTH);
            int enYear = en.get(Calendar.YEAR);
            int enMonth = en.get(Calendar.MONTH);
            int year = enYear  -  stYear;
            findays = 0;
            int month = enYear *  12  + enMonth  -  (stYear  *  12  +  stMonth);
            if (month<4 && month >= 0 ){
                findays = 1;
            }else if (month<7 && month>=4){
                findays = 2;
            }else if (month<10 && month>=7){
                findays = 3;
            }else if (month<13 && month>=10){
                findays = 4;
            }else if (month<25 && month>=13){
                findays = 5;
            }else if (month<36 && month>=15){
                findays = 6;
            }else if (month >=36){
                findays = 7;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String mobile = gys.getLxrsjh();
        String address = gys.getGysdz();
        String socialcode = gys.getGsdjzch();
        String credit_code = gys.getZzjgdmh();
        String taxcode = gys.getSwdjh();
        String status = "0";
        String tbstatus = "0";//0未同步1同步成功2同步失败
        String acccount = "1";
        String note = "备注";
        String fileurl = supply_info.getUrl();
        String invoiceno = yzzkxx.getFph();
        String contraceno = yzzkxx.getHth();
        String orderno = accountcode;
        String applystatus = "0";
        String accountStatus = "0";
        String productcode = "BL";
        String accountamt = yzzkxx.getYzzkje();
        String loanstatus = "2";//默认放款状态为2未确认。

        //......

        //记录apply_info表

        ZyzrNotice applyinfo = new ZyzrNotice();
        applyinfo.setMerId(merid);
        applyinfo.setFinCode(fincode);
        applyinfo.setDebtorCode(debtorcode);
        applyinfo.setDebtorName(debtorname);
        applyinfo.setBankInCode(bankincode);
        applyinfo.setCreditCode(creditcode);
        applyinfo.setCreditName(creditname);
        applyinfo.setCustomerId(customerid);
        applyinfo.setBankCode(bankcode);
        applyinfo.setBankName(bankname);
        applyinfo.setFinProductCode(productcode);
        applyinfo.setFinAmt(finamt);
        applyinfo.setFinDays(findays);
        applyinfo.setFinRate(new BigDecimal("1.00"));
        applyinfo.setFinRatio(new BigDecimal("0.0435"));
        applyinfo.setContactName(contactname);
        applyinfo.setMobile(mobile);
        applyinfo.setAccCount(acccount);
        applyinfo.setNote(note);
        applyinfo.setFileUrl(fileurl);
        applyinfo.setApply_status(applystatus);

        try {
            int AppInf = finApplyMapper.checkAppInfExist(fincode);
            if (AppInf == 0) {

                int applyNum = finApplyMapper.saveFinApplyInfo(applyinfo);

                if (applyNum <= 0) {
                    return YkgyjResult.failed("9999", "申请信息插入apply_info表失败");
                }
                log.info("共插入applyinfo数据条数：" + applyNum);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return YkgyjResult.failed("9999", "申请信息插入apply_info表失败");
        }
        //插account_detail
        AccountDetail accountdetail = new AccountDetail();
        accountdetail.setMerid(merid);
        accountdetail.setDebtor_code(debtorcode);
        accountdetail.setDebtor_name(debtorname);
        accountdetail.setCredit_code(creditcode);
        accountdetail.setCredit_name(creditname);
        accountdetail.setCustomer_id(customerid);
        accountdetail.setBank_in_code(bankincode);
        accountdetail.setBank_code(bankcode);
        accountdetail.setBank_name(bankname);
        accountdetail.setProduct_code(productcode);
        accountdetail.setAccountCode(accountcode);
        accountdetail.setAccountAmt(finamt);//意向金额？账款金额
        accountdetail.setAccStartDate(stdate.toString());
        accountdetail.setAccEndDate(endate.toString());
        accountdetail.setInvoiceNo(invoiceno);
        accountdetail.setContractNo(contraceno);
        accountdetail.setOrderNo(orderno);
        DateFormat dfy=new SimpleDateFormat("yyyy");
        accountdetail.setAccountYear(dfy.format(new Date()));
        accountdetail.setNote(note);
        accountdetail.setLoanStatus(loanstatus);
        accountdetail.setFinCode(fincode);

        try {
            int AccountInf = accountDetailMapper.checkAccountCodeExist(accountcode);
            if (AccountInf == 0) {
                int detailNum = accountDetailMapper.saveAccountDetailInfo(accountdetail);
                if (detailNum <= 0) {
                    return YkgyjResult.failed("9999", "申请信息插入account_detail表失败");
                }
                log.info("共插入applyinfo数据条数：" + detailNum);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return YkgyjResult.failed("9999", "申请信息插入account_detail表失败");
        }

        //插suppliers_info
        SuppliersInfo suppliersinfo = new SuppliersInfo();
        suppliersinfo.setApplyName(creditname);
        suppliersinfo.setApplyCode(creditcode);
        suppliersinfo.setApply_corp_name(applycorpname);
        suppliersinfo.setApply_corp_id_num(applycorpidnum);
        suppliersinfo.setContactName(contactname);
        suppliersinfo.setContact_tel(mobile);
        suppliersinfo.setAddress(address);
        suppliersinfo.setSocial_code(socialcode);
        suppliersinfo.setCredit_code(credit_code);
        suppliersinfo.setTax_code(taxcode);
        suppliersinfo.setStatus(status);
        suppliersinfo.setTbStatus(tbstatus);

        try {
            int SupplierInf = suppliersInfoMapper.checkApplyCodeExist(creditcode);
            if (SupplierInf == 0) {
                int supplierNum = suppliersInfoMapper.saveSuppliersInfo(suppliersinfo);
                if (supplierNum <= 0) {
                    return YkgyjResult.failed("9999", "申请信息插入suppliers_info表失败");
                }
                log.info("共插入suppliersinfo数据条数：" + supplierNum);


                String sqreturn_msg1 = "";
                JSONObject sqresult = suppliersQryService.suppliersQry(merid,debtorcode,creditcode);
                String sqreturn_code = sqresult.getString("return_code");
                log.info("供应商授信信息查询返回returncode"+ sqreturn_code);
                if ("0".equals(sqreturn_code)){
                    //记录授信信息设置表suppliers_info tbstatus为1已授信

                    JSONObject sqdata = sqresult.getJSONObject("data");

                    String sqcreditflag = sqdata.getString("creditflag");
                    if ("1".equals(sqcreditflag)) {
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
                        tbstatus = "1";
                        suppliersinfo.setStatus(tbstatus);
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

                        try {
                            suppliersInfoMapper.saveSuppliersApplyStatus(suppliersinfo);
                        } catch (Exception e) {
                            e.printStackTrace();
                            sqreturn_msg1 = "修改供应商状态失败,请联系银行";
                            return YkgyjResult.failed(sqreturn_code, sqreturn_msg1);
                        }

                        //发起融资申请

                        fseqno = SernoUtil.getSerno();
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
                        flist.setAccountYear(dfy.format(new Date()));
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
                        try {
                            fresult = new RequestBuilder(ApiConstants.API_YKGJY_URL, ApiConstants.API_YKGJY_AIPGW_APPID, ApiConstants.API_YKGJY_PRIKEY)
                                    .apiname(ApiConstants.API_YKGYJ_COM_ICBC_GYJ_YSZK_FINANCINGAPPLY)
                                    .data(freqdata.toJSONString())
                                    .build()
                                    .query();
                        } catch (IllegalAccessException | IOException e) {
                            e.printStackTrace();
                            return YkgyjResult.failed("9999", "请求工银聚融资申请FINANCINGAPPLY失败");
                        }
                        System.out.println(fresult);

                        JSONObject obj = JSON.parseObject(fresult);
                        String freturn_code = obj.getString("return_code");
                        if ("0".equals(freturn_code)) {
                            return YkgyjResult.success("10000", "申请信息发送成功");
                        } else {
                            String freturn_msg = obj.getString("return_msg");
                            return YkgyjResult.failed(freturn_code, freturn_msg);
                        }
                    }else{
                        //无信息记录状态为0设置表suppliers_info status为0每天查询

                        status = "0";
                        String sqreturn_msg = sqresult.getString("return_msg");
                        log.info(sqreturn_msg);
                        sqreturn_msg1 = "暂无授信信息，等待客户经理调查授信";
                        suppliersinfo.setStatus(status);
                        suppliersinfo.setApplyCode(creditcode);
                        try {
                            suppliersInfoMapper.saveSuppliersApplyStatus(suppliersinfo);
                        } catch (Exception e) {
                            e.printStackTrace();
                            sqreturn_msg1 = "修改供应商状态失败，请重新发起";
                            return YkgyjResult.failed(sqreturn_code,sqreturn_msg1);
                        }

                        return YkgyjResult.failed(sqreturn_code,sqreturn_msg1);
                    }


                }else{
                    //无授信信息记录状态为0设置表suppliers_info status为0每天查询

                    status = "0";
                    String sqreturn_msg = sqresult.getString("return_msg");
                    log.info(sqreturn_msg);
                    sqreturn_msg1 = "暂无授信信息，等待客户经理调查授信";
                    suppliersinfo.setStatus(status);
                    suppliersinfo.setApplyCode(creditcode);
                    try {
                        suppliersInfoMapper.saveSuppliersApplyStatus(suppliersinfo);
                    } catch (Exception e) {
                        e.printStackTrace();
                        sqreturn_msg1 = "修改供应商状态失败，请重新发起";
                        return YkgyjResult.failed(sqreturn_code,sqreturn_msg1);
                    }

                    return YkgyjResult.failed(sqreturn_code,sqreturn_msg1);

                }


            }
        } catch (Exception e) {
            e.printStackTrace();
            return YkgyjResult.failed("9999", "申请信息插入suppliers_info表失败");
        }
        //发起融资申请

        fseqno = SernoUtil.getSerno();
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
        flist.setAccountYear(dfy.format(new Date()));
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
        try {
            fresult = new RequestBuilder(ApiConstants.API_YKGJY_URL, ApiConstants.API_YKGJY_AIPGW_APPID, ApiConstants.API_YKGJY_PRIKEY)
                    .apiname(ApiConstants.API_YKGYJ_COM_ICBC_GYJ_YSZK_FINANCINGAPPLY)
                    .data(freqdata.toJSONString())
                    .build()
                    .query();
        } catch (IllegalAccessException | IOException e) {
            e.printStackTrace();
            return YkgyjResult.failed("9999", "请求工银聚融资申请FINANCINGAPPLY失败");
        }
        System.out.println(fresult);

        JSONObject obj = JSON.parseObject(fresult);
        String freturn_code = obj.getString("return_code");
        if ("0".equals(freturn_code)) {
            return YkgyjResult.success("10000", "申请信息发送成功");
        } else {
            String freturn_msg = obj.getString("return_msg");
            return YkgyjResult.failed(freturn_code, freturn_msg);
        }


//        //发送请求到工银聚供应商信息同步接口
//        fseqno = SernoUtil.getSerno();
//      //  JSONObject param1 = new JSONObject();
//        JSONObject data = new JSONObject();
//        JSONObject dataobj = new JSONObject();
//     //   JSONObject param3 = new JSONObject();
//        JSONObject reqdata = new JSONObject();
//        reqdata.put("merid",merid);
//        reqdata.put("timestamp",getTime());
//        reqdata.put("fseqno",fseqno);
//        reqdata.put("field1","");
//        reqdata.put("field2","");
//        reqdata.put("field3","");
//
//
//
//        SupplierList list = new SupplierList();
//        list.setBankInCode(bankincode);
//        list.setCreditCode(creditcode);
//        list.setCreditName(creditname);
//        list.setCustomerId(customerid);
//        list.setBankCode(bankcode);
//        list.setBankName(bankname);
//        list.setFinProductCode(productcode);
//        list.setMakeCreditDate(df.format(new Date()));
//        list.setBelongTo("");
//        list.setInstname("");
//        JSONArray listArray = new JSONArray();
//        listArray.add(list);
//        data.put("list",listArray);
//        data.put("opertype","1");
//        data.put("debtorCode",debtorcode);
//        data.put("debtorName",debtorname);
//        reqdata.put("data",data);
//        log.info("reqdata{}"+reqdata.toString());
//
//       // String apiname = "com.icbc.gyj.yszk.supplierqry";
//        String result = null;
//        try {
//            result = new RequestBuilder(ApiConstants.API_YKGJY_URL,ApiConstants.API_YKGJY_AIPGW_APPID,ApiConstants.API_YKGJY_PRIKEY)
//                    .apiname(ApiConstants.API_YKGYJ_COM_ICBC_GYJ_YSZK_SUPPLIERINFO)
//                    .data(reqdata.toJSONString())
//                    .build()
//                    .query();
//        } catch (IOException | IllegalAccessException e) {
//            e.printStackTrace();
//            return YkgyjResult.failed("9999","请求工银聚供应商信息同步SUPPLIERINFO失败");
//        }
//        System.out.println(result);
//
//        JSONObject objresult = JSON.parseObject(result);
//        String returncode = objresult.getString("return_code");
//        String returnmsg = objresult.getString("return_msg");
//        if ("0".equals(returncode)){
//            //更新商户状态
//            applystatus = "1";
//            applyinfo.setApply_status(applystatus);//0未处理 1供应商信息同步成功,2供应商信息同步失败
//            applyinfo.setFinCode(fincode);
//
//            try {
//                finApplyMapper.saveFinApplyStatus(applyinfo);
//            } catch (Exception e) {
//                e.printStackTrace();
//                return YkgyjResult.failed("9999","更新apply_info表状态失败");
//            }
            //供应商授信信息查询

//            String sqreturn_msg1 = "";
//            JSONObject sqresult = suppliersQryService.suppliersQry(debtorcode,creditcode);
//            String sqreturn_code = sqresult.getString("return_code");
//            log.info("供应商授信信息查询返回returncode"+ sqreturn_code);
//            if ("0".equals(sqreturn_code)){
//                //记录授信信息设置表suppliers_info status为1已授信
//                JSONObject sqdata = sqresult.getJSONObject("data");
//                String sqcreditflag = sqdata.getString("creditflag");
//                if ("1".equals(sqcreditflag)) {
//                    String sqcustomerid = sqdata.getString("customerId");
//                    String sqbankcode = sqdata.getString("bankCode");
//                    String sqbankname = sqdata.getString("bankName");
//                    String sqfinproductcode = sqdata.getString("finProductCode");
//                    String sqmakecreditdate = sqdata.getString("make_credit_date");
//
//                    String sqbelongto = sqdata.getString("belongTo");
//                    String sqinstname = sqdata.getString("instname");
//                    String sqaccno = sqdata.getString("accno");
//                    String sqaccname = sqdata.getString("accname");
//                    String sqopenbank = sqdata.getString("openbank");
//                    String sqcreditamt = sqdata.getString("creditAmt");
//                    String sqmaxperiod = sqdata.getString("maxPeriod");
//                    status = "1";
//                    suppliersinfo.setStatus(status);
//                    suppliersinfo.setCustomerId(sqcustomerid);
//                    suppliersinfo.setBankCode(sqbankcode);
//                    suppliersinfo.setBankName(sqbankname);
//                    suppliersinfo.setFinProductCode(sqfinproductcode);
//                    suppliersinfo.setMake_credit_date(sqmakecreditdate);
//                    suppliersinfo.setCreditflag(sqcreditflag);
//                    suppliersinfo.setBelongTo(sqbelongto);
//                    suppliersinfo.setInstname(sqinstname);
//                    suppliersinfo.setAccno(sqaccno);
//                    suppliersinfo.setAccname(sqaccname);
//                    suppliersinfo.setOpenbank(sqopenbank);
//                    suppliersinfo.setCreditAmt(sqcreditamt);
//                    suppliersinfo.setMaxPeriod(sqmaxperiod);
//                    suppliersinfo.setApplyCode(creditcode);
//
//                    try {
//                        suppliersInfoMapper.saveSuppliersApplyStatus(suppliersinfo);
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                        sqreturn_msg1 = "修改供应商状态失败，请重新发起";
//                        return YkgyjResult.failed(sqreturn_code, sqreturn_msg1);
//                    }
//
//                    //发起融资申请
//
//                    fseqno = SernoUtil.getSerno();
//                    //  JSONObject param1 = new JSONObject();
//                    JSONObject fdata = new JSONObject();
//                    JSONObject fdataobj = new JSONObject();
//                    //   JSONObject param3 = new JSONObject();
//                    JSONObject freqdata = new JSONObject();
//                    freqdata.put("merid", merid);
//                    freqdata.put("timestamp", getTime());
//                    freqdata.put("fseqno", fseqno);
//                    freqdata.put("field1", "");
//                    freqdata.put("field2", "");
//                    freqdata.put("field3", "");
//
//
//                    FinancingList flist = new FinancingList();
//                    flist.setDebtor_code(debtorcode);
//                    flist.setDebtor_name(debtorname);
//                    flist.setCredit_code(creditcode);
//                    flist.setCredit_name(creditname);
//                    flist.setCustomer_id(customerid);
//                    flist.setBank_in_code(bankincode);
//                    flist.setBank_code(bankcode);
//                    flist.setBank_name(bankname);
//                    flist.setProduct_code(productcode);
//                    flist.setAccountCode(accountcode);
//                    flist.setAccountAmt(accountamt); //???应收账款金额  授信金额   账款金额
//                    flist.setAccStartDate(stdate.toString());
//                    flist.setAccEndDate(endate.toString());
//                    flist.setInvoiceNo(invoiceno);
//                    flist.setContractNo(contraceno);
//                    flist.setOrderNo(orderno);
//                    flist.setAccountYear(dfy.format(new Date()));
//                    flist.setNote(note);
//
//                    JSONArray flistArray = new JSONArray();
//                    flistArray.add(flist);
//                    fdata.put("accountList", flistArray);
//                    fdata.put("finCode", fincode);
//                    fdata.put("debtorCode", debtorcode);
//                    fdata.put("debtorName", debtorname);
//                    fdata.put("bankInCode", bankincode);
//                    fdata.put("creditCode", creditcode);
//                    fdata.put("creditName", creditname);
//                    fdata.put("customerId", customerid);
//                    fdata.put("bankCode", bankcode);
//                    fdata.put("bankName", bankname);
//                    fdata.put("finProductCode", productcode);
//                    fdata.put("finAmt", finamt);//意向金额，账款金额？
//                    fdata.put("finDays", findays);
//                    fdata.put("accCount", acccount);
//
//                    freqdata.put("data", fdata);
//                    log.info("reqdata{}" + freqdata.toString());
//                    String fresult = null;
//                    try {
//                        fresult = new RequestBuilder(ApiConstants.API_YKGJY_URL, ApiConstants.API_YKGJY_AIPGW_APPID, ApiConstants.API_YKGJY_PRIKEY)
//                                .apiname(ApiConstants.API_YKGYJ_COM_ICBC_GYJ_YSZK_FINANCINGAPPLY)
//                                .data(freqdata.toJSONString())
//                                .build()
//                                .query();
//                    } catch (IllegalAccessException | IOException e) {
//                        e.printStackTrace();
//                        return YkgyjResult.failed("9999", "请求工银聚融资申请FINANCINGAPPLY失败");
//                    }
//                    System.out.println(fresult);
//
//                    JSONObject obj = JSON.parseObject(fresult);
//                    String freturn_code = obj.getString("return_code");
//                    if ("0".equals(freturn_code)) {
//                        return YkgyjResult.success("10000", "申请信息发送成功");
//                    } else {
//                        String freturn_msg = obj.getString("return_msg");
//                        return YkgyjResult.failed(freturn_code, freturn_msg);
//                    }
//
//
//                }else{
//                    //无授信信息记录状态为0设置表suppliers_info status为0每天查询
//
//                    status = "0";
//                    String sqreturn_msg = sqresult.getString("return_msg");
//                    log.info(sqreturn_msg);
//                    sqreturn_msg1 = "暂无授信信息，等待客户经理调查授信";
//                    suppliersinfo.setStatus(status);
//                    suppliersinfo.setApplyCode(creditcode);
//                    try {
//                        suppliersInfoMapper.saveSuppliersApplyStatus(suppliersinfo);
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                        sqreturn_msg1 = "修改供应商状态失败，请重新发起";
//                        return YkgyjResult.failed(sqreturn_code,sqreturn_msg1);
//                    }
//
//                    return YkgyjResult.failed(sqreturn_code,sqreturn_msg1);
//                }
//            }else{
//                //无授信信息记录状态为0设置表suppliers_info status为0每天查询
//
//                status = "0";
//                String sqreturn_msg = sqresult.getString("return_msg");
//                log.info(sqreturn_msg);
//                sqreturn_msg1 = "暂无授信信息，等待客户经理调查授信";
//                suppliersinfo.setStatus(status);
//                suppliersinfo.setApplyCode(creditcode);
//                try {
//                    suppliersInfoMapper.saveSuppliersApplyStatus(suppliersinfo);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                    sqreturn_msg1 = "修改供应商状态失败，请重新发起";
//                    return YkgyjResult.failed(sqreturn_code,sqreturn_msg1);
//                }
//
//                return YkgyjResult.failed(sqreturn_code,sqreturn_msg1);
//
//            }

//        } else {
//            //记录同步失败原因并返回
//            applystatus = "2";
//            applyinfo.setApply_status(applystatus);//0未处理 1供应商信息同步成功,2供应商信息同步失败
//            applyinfo.setFinCode(fincode);
//
//            try {
//                finApplyMapper.saveFinApplyStatus(applyinfo);
//            } catch (Exception e) {
//                e.printStackTrace();
//                return YkgyjResult.failed("9999","更新apply_info表状态失败");
//            }
//            return YkgyjResult.failed(returncode,returnmsg);
//
//        }


    }
}
