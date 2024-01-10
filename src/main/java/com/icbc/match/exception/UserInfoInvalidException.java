package com.icbc.match.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * 无效用户异常
 */
public class UserInfoInvalidException extends AuthenticationException {
    public UserInfoInvalidException(String msg, Throwable t) {
        super(msg, t);
    }

    public UserInfoInvalidException(String msg) {
        super(msg);
    }
}