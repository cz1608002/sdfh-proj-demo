package com.icbc.match.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

public class HeaderUtil {
	
	private static final Logger log = LoggerFactory.getLogger(HeaderUtil.class);

    public static void printHeaders(HttpServletRequest req) {
        Enumeration<String> headers = req.getHeaderNames();
        while (headers.hasMoreElements()) {
            String key = headers.nextElement();
            log.info(key + " --> " + req.getHeader(key));
        }
    }
}
