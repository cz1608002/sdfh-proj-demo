package com.icbc.match.security;

import com.icbc.match.utils.Base64Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;
import java.util.regex.Pattern;

@Component
@PropertySource(value = "classpath:ctp-security.properties")
public class SecurityValidator {

    private static Logger ctpLog = LoggerFactory.getLogger(SecurityFilter.class);

    public static final String SEPARATOR = ",";

    @Value("${ctp.security.rule.context}")
    private String sessCtxKeywords;

    @Value("${ctp.security.rule.JDKXML}")
    private String JDKXMLKeywords;

    @Value("${ctp.security.rule.commonValidator.paramnamekeyword}")
    private String comParamNameKeyword;

    @Value("${ctp.security.rule.commonValidator.paramvaluekeyword}")
    private String comParamValueKeyword;

    @Value("${ctp.security.rule.commonValidator.paramnameexpression}")
    private String comParamNameExpression;

    @Value("${ctp.security.rule.OGNLKey}")
    private String OGNLKeyKeywords;

    @Value("${ctp.security.rule.context.isOn}")
    private String sessCtxIsOn;

    @Value("${ctp.security.rule.JDKXML.isOn}")
    private String JDKXMLIsOn;

    @Value("${ctp.security.rule.commonValidator.isOn}")
    private String commonValidatorIsOn;

    @Value("${ctp.security.rule.OGNLKey.isOn}")
    private String OGNLKeyIsOn;

    private List<String> parameterNameKeywords = null;
    private List<String> parameterValueKeywords = null;
    private List<String> sessCtxKeyList;
    private List<String> JDKXMLKeyList;
    private List<String> OGNLKeyKeyList;

    @PostConstruct
    public void initProp() {
        if (!"".equals(this.sessCtxKeywords)) {
            String[] splitSessCtxKeywordsArr = this.sessCtxKeywords.split(",");
            this.sessCtxKeyList = Arrays.asList(splitSessCtxKeywordsArr);
            ctpLog.info("sessCtxKeyList setted");
        }

        if (!"".equals(this.JDKXMLKeywords)) {
            String[] splitJDKXMLKeywordArr = this.JDKXMLKeywords.split(",");
            this.JDKXMLKeyList = Arrays.asList(splitJDKXMLKeywordArr);
            ctpLog.info("JDKXMLKeyList setted");
        }

        if (!"".equals(this.comParamNameKeyword)) {
            String[] splitComParamNameKeyArr = this.comParamNameKeyword.split(",");
            this.parameterNameKeywords = Arrays.asList(splitComParamNameKeyArr);
            String[] splitComParamValueKeyArr = this.comParamValueKeyword.split(",");
            this.parameterValueKeywords = Arrays.asList(splitComParamValueKeyArr);
            ctpLog.info("commonValidatorKeyList setted");
        }

        if (!"".equals(this.OGNLKeyKeywords)) {
            String[] splitOGNLKeyKeyArr = this.OGNLKeyKeywords.split(",");
            this.OGNLKeyKeyList = Arrays.asList(splitOGNLKeyKeyArr);
            ctpLog.info("OGNLKeyKeyList setted");
        }
    }

    public String doFilterValue(String value) {
        if ("true".equals(this.commonValidatorIsOn)) {
            if ((isCommonValidatorContainKeyWords(value))) {
                return "F-CTP-999-0001";
            }
        }
        if ("true".equals(this.sessCtxIsOn)) {
            for (String s : this.sessCtxKeyList) {
                if (value.trim().contains(s.toLowerCase())) {
                    return "F-CTP-999-0002";
                }
            }
        }
        if ("true".equals(this.OGNLKeyIsOn)) {
            for (String s : this.OGNLKeyKeyList) {
                if (value.contains(s.toLowerCase())) {
                    return "F-CTP-999-0004";
                }
            }
        }
        return "0";
    }

    public String doFilter(ServletRequest req) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        if ((!doCommonFilter(request))) {
            ctpLog.info("F-CTP-999-0001  reject the request!");
            return "F-CTP-999-0001";
        }
        if ((!doSessCtxFilter(request))) {
            ctpLog.info("F-CTP-999-0002 reject the request!");
            return "F-CTP-999-0002";
        }
        if ((!doJDKXMLFilter(request))) {
            ctpLog.info("F-CTP-999-0003 reject the request!");
            return "F-CTP-999-0003";
        }
        if ((!doOGNLKeyFilter(request))) {
            ctpLog.info("F-CTP-999-0004 reject the request!");
            return "F-CTP-999-0004";
        }
        return "0";
    }

    private boolean doCommonFilter(HttpServletRequest request) throws IOException, ServletException {
        if (!"true".equals(this.commonValidatorIsOn)) {
            return true;
        }

        Enumeration<?> enumer = request.getParameterNames();

        if (enumer != null) {
            while (enumer.hasMoreElements()) {
                String key = (String) enumer.nextElement();
                String[] paramValues = request.getParameterValues(key);
                if (!validParameterNameExpression(key)) {
                    ctpLog.error(">Commonvalidator valid key error: " + key);
                    return false;
                }
                if (!validParameterNameKeywords(key)) {
                    ctpLog.error(">Commonvalidator valid key error:: " + key);
                    return false;
                }
                if (paramValues != null) {
                    for (String pValue : paramValues) {
                        if (isCommonValidatorContainKeyWords(pValue)) {
                            ctpLog.info("执行doCommonFilter方法时，访问端地址:" + request.getRequestURL() + " SessionID:" + request.getParameter("dse_sessionId") + "发现请求参数值" + pValue + "含有非法字符");
                            return false;
                        }
                    }
                }
            }
        }

        return true;
    }

    private boolean isCommonValidatorContainKeyWords(String value) {
        for (String keyword : this.parameterValueKeywords) {
            boolean flag = validateCompose(value, keyword);

            if (flag) {
                ctpLog.info("commonvalidator 非法字符： " + keyword);
                return true;
            }
        }
        return false;
    }

    private boolean validateCompose(String param, String key) {
        if (StringUtils.isEmpty(param)) {
            return false;
        }
        String temp = param.toUpperCase();
        if (key.contains("|")) {
            String[] t = key.split("\\|");
            if (t.length == 2) {
                return (temp.contains(t[0]) && temp.contains(t[1])) ? true : false;
            } else {
                return temp.contains(key) ? true : false;
            }
        } else {
            return temp.contains(key) ? true : false;
        }
    }


    private boolean validParameterNameExpression(String paramName) {
        if (paramName == null || paramName.equals("")) {
            return true;
        }
        Pattern pattern = Pattern.compile(this.comParamNameExpression);
        return pattern.matcher(paramName).matches();
    }

    private boolean validParameterNameKeywords(String paramName) {
        if (StringUtils.isEmpty(paramName)) {
            return true;
        }
        for (String key : parameterNameKeywords) {
            if (paramName.toUpperCase().contains(key)) {
                return false;
            }
        }
        return true;
    }

    //SessCtxFilter
    private boolean doSessCtxFilter(HttpServletRequest request) throws IOException, ServletException {
        if (!"true".equals(this.sessCtxIsOn)) {
            return true;
        }
        Enumeration<?> enumer = request.getParameterNames();
        if (enumer != null) {
            String paramName;
            while (enumer.hasMoreElements()) {
                paramName = ((String) enumer.nextElement()).toLowerCase();
                for (String s : this.sessCtxKeyList) {
                    if (paramName.trim().startsWith(s.toLowerCase())) {
                        ctpLog.info("执行doSessCtxFilter方法时，访问端地址:" + request.getRequestURL() + " SessionID:" + request.getParameter("dse_sessionId") + " 发现请求参数" + paramName + "含有非法字符");
                        return false;
                    }
                }
            }
        }
        return true;
    }

    private boolean doOGNLKeyFilter(HttpServletRequest request) throws IOException, ServletException {
        if (!"true".equals(this.OGNLKeyIsOn)) {
            return true;
        }
        Enumeration<?> enumer = request.getParameterNames();
        if (enumer != null) {
            String paramName;
            while (enumer.hasMoreElements()) {
                paramName = ((String) enumer.nextElement()).toLowerCase();
                for (String s : this.OGNLKeyKeyList) {
                    if (paramName.contains(s.toLowerCase())) {
                        ctpLog.info("执行doOGNLKeyFilter方法时，访问端地址:" + request.getRequestURL() + " SessionID:" + request.getParameter("dse_sessionId") + "发现请求参数" + paramName + "含有非法字符");
                        return false;
                    }
                }
            }
        }
        return true;
    }

    private boolean doJDKXMLFilter(HttpServletRequest request) throws IOException, ServletException {
        if (!"true".equals(this.JDKXMLIsOn)) {
            return true;
        }
        Enumeration<?> enumer = request.getParameterNames();
        if (enumer != null) {
            String flowContextStr;
            while (enumer.hasMoreElements()) {
                String paramName = (String) enumer.nextElement();
                if ((paramName.endsWith("_flow_context_string")) && (!"".equals(request.getParameter(paramName)))) {
                    flowContextStr = request.getParameter(paramName);
                    flowContextStr = new String(Base64Util.decode(flowContextStr)).toLowerCase();
                    for (String s : this.JDKXMLKeyList) {
                        if (flowContextStr.contains(s.toLowerCase())) {
                            ctpLog.info("执行doJDKXMLFilter方法时，访问端地址:" + request.getRequestURL() + " SessionID:"
                                    + request.getParameter("dse_sessionId") + "发现请求参数值" + flowContextStr + "含有非法字符");
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }
}
