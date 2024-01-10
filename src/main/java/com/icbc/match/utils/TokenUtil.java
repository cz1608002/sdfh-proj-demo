package com.icbc.match.utils;

import com.icbc.match.entry.SecurityUser;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

public class TokenUtil {
    public static String getUserId() {
        UsernamePasswordAuthenticationToken authentication = (UsernamePasswordAuthenticationToken) SecurityContextHolder
                .getContext().getAuthentication();
        SecurityUser user = (SecurityUser) authentication.getPrincipal();
        return user.getUuid();
    }

    public static String getMobile() {
        UsernamePasswordAuthenticationToken authentication = (UsernamePasswordAuthenticationToken) SecurityContextHolder
                .getContext().getAuthentication();
        SecurityUser user = (SecurityUser) authentication.getPrincipal();
        return user.getMobile();
    }
}
