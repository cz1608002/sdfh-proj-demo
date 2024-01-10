package com.icbc.match.security;

import com.icbc.match.entry.ResultCode;
import com.icbc.match.utils.ResponseUtil;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;

/**
 * Created by meng on 2018/3/24.
 */
@Component
public class JwtAuthenticationHandler implements AuthenticationEntryPoint, Serializable {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws ServletException, IOException {
        ResponseUtil.writeResponse(response, ResultCode.AUTH_FAIL.toJson());
    }
}
