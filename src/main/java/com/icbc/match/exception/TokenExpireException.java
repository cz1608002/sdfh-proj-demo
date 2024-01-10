package com.icbc.match.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * Jwt Token超时异常
 */
public class TokenExpireException extends AuthenticationException {
    public TokenExpireException(String msg, Throwable t) {
        super(msg, t);
    }

    public TokenExpireException(String msg) {
        super(msg);
    }
}