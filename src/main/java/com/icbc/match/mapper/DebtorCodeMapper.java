package com.icbc.match.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.icbc.match.entity.CreditQryList;
import com.icbc.match.entity.DebtorCode;
import com.icbc.match.entity.ZyzrNotice;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Mapper
@Component
public interface DebtorCodeMapper  extends BaseMapper<DebtorCode> {

    /**
     * 查询核心企业信息
     *
     * @param debtorCode
     */
    @Select("SELECT t.merid,t.debtorCode,t.debtorName,t.bankInCode,t.bankCode,t.bankName FROM debtor_info t " +
            "where t.debtorCode = #{debtorCode}")
    ArrayList<DebtorCode> selectDebtorInfo(@Param("debtorCode") String debtorCode);
}
