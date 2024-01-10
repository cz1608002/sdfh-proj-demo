package com.icbc.match.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.icbc.match.entity.CreditQryList;
import com.icbc.match.entity.SuppliersInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Mapper
@Component
public interface CreditQryMapper extends BaseMapper<CreditQryList> {

    /**
     * 查询未授信商户applyCode信息
     *
     * @param
     */
    @Select("select t.finCode,t.debtorCode,t.debtorName,t.bankInCode," +
            "t.creditCode,t.creditName,t.customerId,t.bankCode," +
            "t.bankName,t.finProductCode,t.finAmt,t.finDays,t.finRate," +
            "t.finRatio,t.contactName,t.mobile,t.accCount,c.debtor_code," +
            "c.debtor_name,c.credit_code,c.credit_name,c.customer_id," +
            "c.bank_in_code,c.bank_code,c.bank_name,c.product_code," +
            "c.accountCode,c.accountAmt,c.accStartDate,c.accEndDate," +
            "c.invoiceNo,c.contractNo,c.orderNo,c.accountYear,c.accountStatus," +
            "c.note FROM apply_info t INNER JOIN account_detail c" +
            " ON t.finCode = c.finCode " +
            "WHERE t.creditCode IN (select tt.applyCode as creditCode from suppliers_info tt where" +
            " tt.`status` = '0')")
    ArrayList<CreditQryList> selectCreditQryList();

    /**
     * 查询未授信商户applyCode信息
     *
     * @param
     */
    @Select("select t.merid,t.accountCode from account_detail t " +
            "where t.loanStatus = '2'")
    ArrayList<CreditQryList> selectAccountCodeList();

    @Select("select t.accountCode from account_detail t " +
            "where t.loanStatus = '1' and t.repayStatus = '1'")
    ArrayList<CreditQryList> selectAccountCoderList();

}
