package com.icbc.match.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.icbc.match.entity.ZyzrNotice;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface FinApplyMapper extends BaseMapper<ZyzrNotice> {
    @Insert("INSERT INTO apply_info  (merid,finCode,debtorCode,debtorName,bankInCode,creditCode," +
            "creditName,customerId,bankCode,bankName,finProductCode,finAmt,finDays,finRate,finRatio,"+
            "contactName,mobile,accCount,note,fileUrl)" +
            "values (#{merId},#{finCode},#{debtorCode},#{debtorName},#{bankInCode},#{creditCode},"+
            "#{creditName},#{customerId},#{bankCode},#{bankName},#{finProductCode},#{finAmt}," +
            "#{finDays},#{finRate},#{finRatio},#{contactName},#{mobile},#{accCount},#{note},#{fileUrl})")
    int saveFinApplyInfo(ZyzrNotice zyzrNotice);

    @Select("select count(1) from apply_info t  where t.finCode = #{finCode}")
    int checkAppInfExist(@Param("finCode") String finCode);

    @Update("update apply_info t " +
            "SET t.apply_status = #{apply_status} " +
            "where t.finCode = #{finCode}")
    void saveFinApplyStatus(ZyzrNotice zyzrNotice);

}
