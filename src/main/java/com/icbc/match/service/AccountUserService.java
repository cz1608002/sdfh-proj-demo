package com.icbc.match.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.icbc.match.entity.AccountUser;
import com.icbc.match.entry.Result;
import com.icbc.match.mapper.AccountUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountUserService {

    @Autowired
    private AccountUserMapper accountUserMapper;

    /**
     * 存储用户信息
     *
     * @param userId
     * @param mobile
     * @return
     */
    public Result saveUserInfo(String userId, String mobile) {


        AccountUser accountUser = new AccountUser();
        accountUser.setUserId(userId);
        accountUser.setMobile(mobile);

        accountUserMapper.insert(accountUser);

        return Result.success();
    }


    /**
     * 根据手机号查询是否存在登录信息
     *
     * @param mobile
     * @return
     */
    public AccountUser corpUserInfo(String mobile) {

        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("MOBILE", mobile);

        AccountUser accountUser = accountUserMapper.selectOne(queryWrapper);


        return accountUser;
    }

    /**
     * 根据userId查询客户信息
     *
     * @param userId
     * @return
     */
    public AccountUser getUserInfoByUserId(String userId) {

        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("USER_ID", userId);

        AccountUser accountUser = accountUserMapper.selectOne(queryWrapper);


        return accountUser;
    }




}
