package com.icbc.match.security;


import com.icbc.match.config.Apollo;
import com.icbc.match.exception.TransactionException;
import com.icbc.match.utils.SM2Utils;
import com.icbc.match.utils.Util;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class IcbcSmService {


    private String bankSm2PublicKey;

    private String corpSm2PublicKey;
    private String corpSm2PrivateKey;


    public Map<String, String> encrypt(Map<String, String> data) {
        String initSecretKey = Util.getNonceStr(16);
        Map resMap = new HashMap();
        for (Object key : data.keySet()) {
            resMap.put(key.toString(), Util.sm4EncryptData_CBC(initSecretKey, data.get(key)));
        }
        String secretKey = null;
        try {
            secretKey = SM2Utils.encrypt(Util.hexToByte(bankSm2PublicKey), initSecretKey.getBytes());
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new TransactionException("-1", "SM加密失败");
        }
        resMap.put("secretKey", secretKey);
        return resMap;

    }

    public Map<String, String> decrypt(Map<String, String> data) {


        /**
         * 解密
         */
        Map resMap = new HashMap();
        String secretKey = data.get("secretKey");

        String secretKeyPri = null;
        try {
            secretKeyPri = Util.getHexString(SM2Utils.decryptBase64(Util.hexToByte(corpSm2PrivateKey), secretKey));
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new TransactionException("-1", "SM解密失败");
        }

        data.remove("secretKey");

        for (Object key : data.keySet()) {
            resMap.put(key.toString(), Util.decryptData_CBC(secretKeyPri, data.get(key)));
        }
        return resMap;

    }


}
