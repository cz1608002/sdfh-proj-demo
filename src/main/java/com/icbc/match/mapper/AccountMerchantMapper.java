package com.icbc.match.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.icbc.match.entity.AccountMerchant;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface AccountMerchantMapper extends BaseMapper<AccountMerchant> {

    @Select("select count(1) from account_merchant where merchant_id = #{orgNo}")
    int checkMerExist(@Param("orgNo") String orgNo);
}
