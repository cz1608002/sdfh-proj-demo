package com.icbc.match.utils;

import com.icbc.match.entry.SecurityUser;
import com.icbc.match.exception.TransactionException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

public class UserUtil {


    /**
     * 通过SecurityContextHolder获取当前访问用户
     * @return 当前访问用户对象
     */
    public static SecurityUser getCurrentUser() {
        UsernamePasswordAuthenticationToken authentication = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        SecurityUser user = (SecurityUser) authentication.getPrincipal();
        if (user == null) {
            throw new TransactionException("-1", "会话信息异常");
        }
        return user;
    }
}
