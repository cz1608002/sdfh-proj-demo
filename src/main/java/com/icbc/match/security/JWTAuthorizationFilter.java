package com.icbc.match.security;

import com.icbc.match.entry.ResultCode;
import com.icbc.match.entry.SecurityUser;
import com.icbc.match.exception.TokenExpireException;
import com.icbc.match.exception.UserInfoInvalidException;
import com.icbc.match.utils.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

import static com.icbc.match.config.SecurityConstants.HEADER_STRING;
import static com.icbc.match.config.SecurityConstants.TOKEN_PREFIX;


public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

    @Autowired
    private JwtTokenService jwtTokenService;

    public JWTAuthorizationFilter(AuthenticationManager authManager, JwtTokenService jwtTokenService) {
        super(authManager);
        this.jwtTokenService = jwtTokenService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req,
                                    HttpServletResponse res,
                                    FilterChain chain) throws ServletException, IOException {
        String token = req.getHeader(HEADER_STRING.toLowerCase());
 //       chain.doFilter(req, res);

        if (!validToken(token)) {
            chain.doFilter(req, res);
            return;
        }

        String realToken = token.substring(TOKEN_PREFIX.length());

        String path = req.getServletPath();

        try {
            UsernamePasswordAuthenticationToken authentication = getAuthentication(path, realToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            chain.doFilter(req, res);
        } catch (TokenExpireException e) {
            ResponseUtil.writeResponse(res, ResultCode.AUTH_EXPIRE.toJson());
        } catch (UserInfoInvalidException e) {
            ResponseUtil.writeResponse(res, ResultCode.AUTH_FAIL.toJson());
        } catch (Exception e) {
            ResponseUtil.writeResponse(res, ResultCode.FAILURE.toJson());
        }
    }

    private UsernamePasswordAuthenticationToken getAuthentication(String path, String token) throws TokenExpireException {
        SecurityUser user;
        try {
            user = jwtTokenService.getUserFromToken(token);
        } catch (TokenExpireException e) {
            throw e;
        }

        if (user == null) {
            return null;
        }

        return new UsernamePasswordAuthenticationToken(user, null, new ArrayList<>());
    }

    private boolean validToken(String token) {
        if (token == null || !token.startsWith(TOKEN_PREFIX)) {
            return false;
        }
        return true;
    }

}
