package com.icbc.match.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

public class ParamsUtil {

    private static final Logger log = LoggerFactory.getLogger(ParamsUtil.class);

    public static void printHeaders(HttpServletRequest req) {
        Enumeration<String> parameterNames = req.getParameterNames();
        while (parameterNames.hasMoreElements()) {
            String key = parameterNames.nextElement();
            log.info(key + " --> " + req.getParameter(key));
        }
    }


}
