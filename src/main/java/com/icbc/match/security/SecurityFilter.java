package com.icbc.match.security;

import com.icbc.match.utils.HeaderUtil;
import com.icbc.match.utils.ParamsUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SecurityFilter implements Filter {

    @Autowired
    private SecurityValidator validator;

    private static final Logger log = LoggerFactory.getLogger(SecurityFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        commonValidate(request, response, chain);
    }

    private void commonValidate(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        String url = req.getRequestURL().toString();
        log.info(String.format("filter url: %s , ip: %s", url, req.getHeader("x-forwarded-for")));

        HeaderUtil.printHeaders(req);
        ParamsUtil.printHeaders(req);

        String errorCode = validator.doFilterValue(url);

        if (!"0".equals(errorCode)) {
            res.getWriter().write(errorCode + " reject the request!");
        } else {
            errorCode = validator.doFilter(req);
            if (errorCode.equals("0")) {
                chain.doFilter(req, response);
            } else {
                res.getWriter().write(errorCode + " reject the request!");
            }
        }
    }

    @Override
    public void destroy() {

    }
}
