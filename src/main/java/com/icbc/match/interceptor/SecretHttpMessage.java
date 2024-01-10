package com.icbc.match.interceptor;

import com.icbc.match.config.ApiConstants;
import com.icbc.match.utils.Base64Util;
import com.icbc.match.utils.RSAUtil;
import org.apache.commons.io.IOUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpInputMessage;

import java.io.IOException;
import java.io.InputStream;
import java.security.PrivateKey;

public class SecretHttpMessage implements HttpInputMessage {
    private InputStream body;
    private HttpHeaders headers;

    public SecretHttpMessage(HttpInputMessage inputMessage, String privKey) throws Exception {
        String data = IOUtils.toString(inputMessage.getBody(), "UTF-8");
        String decrypt = rsaDecode(data, privKey);
        this.body = IOUtils.toInputStream(decrypt, "UTF-8");
    }

    /**
     * 将前端传入经过RSA加密数据解密
     *
     * @param encodeData
     * @param
     * @return
     */
    public String rsaDecode(String encodeData, String privKey) {
        try {
            PrivateKey privateKey = RSAUtil.loadPrivateKey(privKey);
            return new String(RSAUtil.decryptData(Base64Util.decode(encodeData), privateKey), ApiConstants.CHAR_SET);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public InputStream getBody() throws IOException {
        return body;
    }

    @Override
    public HttpHeaders getHeaders() {
        return headers;
    }
}