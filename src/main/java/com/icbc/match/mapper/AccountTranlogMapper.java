package com.icbc.match.mapper;

import com.icbc.match.entity.AccountTranlog;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;


@Mapper
@Component
public interface AccountTranlogMapper extends BaseMapper<AccountTranlog> {

}
