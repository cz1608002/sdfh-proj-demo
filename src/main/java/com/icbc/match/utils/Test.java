package com.icbc.match.utils;

import java.io.IOException;

/**
 * SM2+SM4加解密例子，仅供参考，相关配置、处理请务必根据项目调整选择优化方案<br>
 * 先用随机生成的SM4密钥加密敏感数据（对称加密），再用SM2加密用于敏感数据加密的SM4密钥（非对称加密）
 */
public class Test {

    public static void main(String[] args) throws Exception {
        encryptAndDecrypt();
    }


    /**
     * 加密测试
     *
     * @throws IOException
     */
    public static void encrypt() throws IOException {
        /**
         * 加密
         */
        String certNo = "123456489797";
        String accountName = "加密";
        // 每次请求生成一个SM4 随机密钥（明文）
        String initSecretKey = Util.getNonceStr(16);
        //String initSecretKey = "93wqujhuwwyqldjl";
        System.out.println("init sm4 secretKey :" + initSecretKey);

        // 使用SM4密钥明文对敏感数据加密
        System.out.println("明文certNo: " + certNo);
        System.out.println("明文accountName: " + accountName);
        certNo = Util.sm4EncryptData_CBC(initSecretKey, certNo);
        accountName = Util.sm4EncryptData_CBC(initSecretKey, accountName);
        System.out.println("加密后certNo: " + certNo);
        System.out.println("加密后accountName: " + accountName);

        // SM2加密的SM4密文，赋值给银行接口的secretKey字段
        // SM2公钥通常是130位，如果是128位则在前面加04
        String secretKey = SM2Utils.encrypt(Util.hexToByte(Util.PUBK), initSecretKey.getBytes());
        System.out.println("SM2公钥加密的SM4对称密钥 secretKey: " + secretKey);
    }

    /**
     * 加密和解密测试
     *
     * @throws IOException
     */
    public static void encryptAndDecrypt() throws IOException {
        /**
         * 加密
         */
        String certNo = "1";
        String accountName = "张三";
        // 每次请求生成一个SM4 随机密钥（明文）
        String initSecretKey = Util.getNonceStr(16);
        //String initSecretKey = "93wqujhuwwyqldjl";
        System.out.println("init sm4 secretKey :" + initSecretKey);

        // 使用SM4密钥明文对敏感数据加密
        System.out.println("明文certNo: " + certNo);
        System.out.println("明文accountName: " + accountName);
        certNo = Util.sm4EncryptData_CBC(initSecretKey, certNo);
        accountName = Util.sm4EncryptData_CBC(initSecretKey, accountName);
        System.out.println("加密后certNo: " + certNo);
        System.out.println("加密后accountName: " + accountName);

        // SM2加密SM4明文，生成SM4密文，赋值给银行接口的secretKey字段
        String secretKey = SM2Utils.encrypt(Util.hexToByte(Util.PUBK), initSecretKey.getBytes());
        System.out.println("SM2公钥加密的SM4对称密钥 secretKey: " + secretKey);

        /**
         * 解密
         */
        // 用合作方私钥对secretKey解密，得到SM4(Base64)
        String secretKeyPri = Util.getHexString(SM2Utils.decryptBase64(Util.hexToByte(Util.PRK), secretKey));
        // 使用SM4的明文（secretKeyPri）对数据解密
        certNo = Util.decryptData_CBC(secretKeyPri, certNo);
        accountName = Util.decryptData_CBC(secretKeyPri, accountName);
        System.out.println("解密后certNo: " + certNo);
        System.out.println("解密后accountName: " + accountName);
    }

    /**
     * 根据SM2公钥加密的SM4对称密钥（secretKey）使用私钥进行解密
     *
     * @throws Exception
     */
    public static void decrypt() throws Exception {
        String secretKey = "gZhWcPxXapYkWvA8taSK2RPP1FfByra/bFSuwHm99jjImOY9UMbtOD8jcIiPrYSt41K4w+lFMtKgmnHx/0BhC8WE6KJ3VYYgsoBuaizDGIiz/m0j0fRMcoYQSz/Yr+uJv1SSvHKXA/NgPWlpyCixwg==";
        String privateKey = "0CFB659B912143250727264BE992AA5FD4C9AA7E7974DACB00DCC76A231EF7FF";
        secretKey = Util.getHexString(SM2Utils.decryptBase64(Util.hexToByte(privateKey), secretKey));
        System.out.println(secretKey);
        String mediumId = "qF8Po5iHrwNhoIEcIUqGhFwjhZkVwDYNr+tDSWqsV3w=";
        mediumId = Util.decryptData_CBC(secretKey, mediumId);
        System.out.println("mediumId:" + mediumId);
    }

    /**
     * 获取明文公私钥
     *
     * @throws Exception
     */
    public static void getSM2Key() throws Exception {
        SM2Utils.generateKeyPair();
    }


	/*public static void main(String[] args) throws Exception {
		encryptAndDecrypt();
	}*/

}
