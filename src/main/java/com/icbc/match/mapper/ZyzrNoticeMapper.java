package com.icbc.match.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.icbc.match.entity.AccountUser;
import com.icbc.match.entity.CreditQryList;
import com.icbc.match.entity.SuppliersInfo;
import com.icbc.match.entity.ZyzrNotice;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Mapper
@Component
public interface ZyzrNoticeMapper extends BaseMapper<ZyzrNotice> {

    @Update("UPDATE apply_info t  " +
            "SET t.ntType = #{ntType}, " +
            "t.recAccno = #{recAccno}, " +
            "t.recAccname = #{recAccname}, " +
            "t.recBankname = #{recBankname}, " +
            "t.ntDate =  #{ntDate}, " +
            "t.ntcount =  #{ntcount}," +
            "t.notifyno = #{notifyno} " +
            "WHERE " +
            "t.finCode = #{finCode}")
    int saveZyzrNoticeInfo(ZyzrNotice zyzrNotice);

    /**
     * 查询notifyno
     *
     * @param finCode
     */
    @Select("SELECT t.merid,t.notifyno,t.debtorCode FROM apply_info t " +
            "where t.finCode = #{finCode}")
    ArrayList<CreditQryList> selectNotifyNo(@Param("finCode") String finCode);

    @Select("SELECT t.finCode,t.contractNo FROM account_detail t " +
            "where t.accountCode = #{accountCode}")
    ArrayList<CreditQryList> selectFinCode(@Param("accountCode") String accountCode);

    @Select("SELECT t.finCode FROM account_detail t " +
            "where t.accountCode = #{accountCode} and t.repayStatus != '3'")
    String selectFinCodes(@Param("accountCode") String accountCode);
}
