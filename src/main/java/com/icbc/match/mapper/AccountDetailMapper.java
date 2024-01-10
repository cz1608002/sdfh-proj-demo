package com.icbc.match.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.icbc.match.entity.AccountDetail;
import com.icbc.match.entity.ZyzrNotice;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface AccountDetailMapper extends BaseMapper<AccountDetail> {
    @Insert("INSERT INTO account_detail  (merid,debtor_code,debtor_name,credit_code,credit_name," +
            "customer_id,bank_in_code,bank_code,bank_name,product_code,accountCode," +
            "accountAmt,accStartDate,accEndDate,invoiceNo,contractNo,orderNo,accountYear," +
            "accountStatus,note,loanStatus,repayStatus,finCode)" +
            "values (#{merid},#{debtor_code},#{debtor_name},#{credit_code},#{credit_name}," +
            "#{customer_id},#{bank_in_code},#{bank_code},#{bank_name},#{product_code},#{accountCode}," +
            "#{accountAmt},#{accStartDate},#{accEndDate},#{invoiceNo},#{contractNo},#{orderNo},#{accountYear}," +
            "#{accountStatus},#{note},#{loanStatus},#{repayStatus},#{finCode})")
    int saveAccountDetailInfo(AccountDetail accountDetail);

    @Select("select count(1) from account_detail t  where t.accountCode = #{accountCode} ")
    int checkAccountCodeExist(@Param("accountCode") String accountCode);
    @Select("select count(1) from account_detail t  where t.accountCode = #{accountCode} and finCode = #{finCode}")
    int checkAccountCodeExist2(@Param("accountCode") String accountCode,@Param("finCode") String finCode);

    @Update("UPDATE account_detail t SET " +
            "t.loanStatus = #{loanStatus} " +
            "where accountCode = #{accountCode}")
    int updateLoanStatus(@Param("loanStatus") String loanStatus,@Param("accountCode") String accountCode);

    @Update("UPDATE account_detail t SET " +
            "t.repayStatus = #{repayStatus} " +
            "where accountCode = #{accountCode}")
    int updateRepayStatus(@Param("repayStatus") String repayStatus,@Param("accountCode") String accountCode);

    @Update("UPDATE account_detail t SET " +
            "t.bak1 = #{bak1} " +
            "where accountCode = #{accountCode}")
    int updateLoanBak1(@Param("bak1") String bak1,@Param("accountCode") String accountCode);

    @Update("UPDATE account_detail t SET " +
            "t.bak1 = #{bak1}," +
            "t.loanStatus = #{loanStatus} " +
            "where accountCode = #{accountCode}")
    int updateLoanStatus1(@Param("bak1") String bak1,@Param("loanStatus") String loanStatus,@Param("accountCode") String accountCode);

    @Update("UPDATE account_detail t SET " +
            "t.bak2 = #{bak2},  " +
            "t.repayStatus = #{repayStatus} " +
            "where accountCode = #{accountCode}")
    int updateLoanBak2(@Param("bak2") String bak2,@Param("repayStatus") String repayStatus,@Param("accountCode") String accountCode);

    @Update("UPDATE account_detail t SET " +
            "t.bak2 = #{bak2}," +
            "t.repayStatus = #{repayStatus} " +
            "where accountCode = #{accountCode}")
    int updateRepayStatus1(@Param("bak2") String bak2,@Param("repayStatus") String repayStatus,@Param("accountCode") String accountCode);


}
