package com.icbc.match.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.icbc.match.entity.AccountUser;
import com.icbc.match.entity.SuppliersInfo;
import com.icbc.match.entity.ZyzrNotice;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Mapper
@Component
public interface SuppliersInfoMapper extends BaseMapper<SuppliersInfo> {

    @Insert("INSERT INTO suppliers_info  (applyName,applyCode,apply_corp_name,apply_corp_id_num,contactName," +
            "contact_tel,address,social_code,credit_code,tax_code,status)" +
            "values (#{applyName},#{applyCode},#{apply_corp_name},#{apply_corp_id_num},#{contactName}," +
            "#{contact_tel},#{address},#{social_code},#{credit_code},#{tax_code},#{status})")
    int saveSuppliersInfo(SuppliersInfo suppliersInfo);

    @Select("select count(1) from suppliers_info t  where t.applyCode = #{applyCode}")
    int checkApplyCodeExist(@Param("applyCode") String applyCode);

    @Select("select count(1) from suppliers_info t  where t.applyCode = #{applyCode} and t.tbStatus = '1'")
    int checkApplyCodeExist2(@Param("applyCode") String applyCode);
    @Update("update suppliers_info t " +
            "SET t.tbstatus = #{tbStatus} " +
            "where t.applyCode = #{applyCode}")
    void saveTbStatus(SuppliersInfo suppliersInfo);

    @Update("update suppliers_info t " +
            "SET t.status = #{status}," +
            "t.customerId = #{customerId}, " +
            "t.bankCode = #{bankCode}, " +
            "t.bankName = #{bankName}, " +
            "t.finProductCode = #{finProductCode}, " +
            "t.make_credit_date = #{make_credit_date}, " +
            "t.creditflag = #{creditflag}, " +
            "t.belongTo = #{belongTo}, " +
            "t.instname = #{instname}, " +
            "t.accno = #{accno}, " +
            "t.accname = #{accname}, " +
            "t.openbank = #{openbank}, " +
            "t.creditAmt = #{creditAmt}, " +
            "t.maxPeriod = #{maxPeriod} " +
            "where t.applyCode = #{applyCode}")
    void saveSuppliersApplyStatus(SuppliersInfo suppliersInfo);


    /**
     * 查询未授信商户applyCode信息
     *
     * @param
     */
    @Select("select t.finCode,t.creditName,c.accountCode " +
            "FROM apply_info t INNER JOIN account_detail c" +
            "ON t.creditCode = c.credit_code " +
            "WHERE t.creditCode IN " +
            "(select tt.applyCode as creditCode from suppliers_info tt where" +
            "tt.`status` = '0')")
    ArrayList<SuppliersInfo> selectSuppliersList();
}

