package com.icbc.match.mapper;

import com.icbc.match.entity.AccountSms;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;


@Mapper
@Component
public interface AccountSmsMapper extends BaseMapper<AccountSms> {

}
