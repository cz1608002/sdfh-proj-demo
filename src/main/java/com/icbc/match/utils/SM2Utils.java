package com.icbc.match.utils;

import org.bouncycastle.crypto.AsymmetricCipherKeyPair;
import org.bouncycastle.crypto.params.ECPrivateKeyParameters;
import org.bouncycastle.crypto.params.ECPublicKeyParameters;
import org.bouncycastle.math.ec.ECPoint;
import sun.misc.BASE64Decoder;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;

public class SM2Utils
{
	//生成随机秘钥对
	public static void generateKeyPair() throws UnsupportedEncodingException, IllegalArgumentException{
		SM2 sm2 = SM2.Instance();
		AsymmetricCipherKeyPair key = sm2.ecc_key_pair_generator.generateKeyPair();
		ECPrivateKeyParameters ecpriv = (ECPrivateKeyParameters) key.getPrivate();
		ECPublicKeyParameters ecpub = (ECPublicKeyParameters) key.getPublic();
		BigInteger privateKey = ecpriv.getD();
		ECPoint publicKey = ecpub.getQ();
		
		
		System.out.println("公钥:" + Util.byteToHex(publicKey.getEncoded()));
		System.out.println("私钥: " + Util.byteToHex(privateKey.toByteArray()));
		
		
		//System.out.println("公钥明文: "+new String(Util.hexToByte(Util.byteToHex(publicKey.getEncoded())), "UTF-8"));
	}
	
	//数据加密
	public static String encrypt(byte[] publicKey, byte[] data) throws IOException
	{
		if (publicKey == null || publicKey.length == 0)
		{
			return null;
		}
		
		if (data == null || data.length == 0)
		{
			return null;
		}
		
		byte[] source = new byte[data.length];
		System.arraycopy(data, 0, source, 0, data.length);
		
		Cipher cipher = new Cipher();
		SM2 sm2 = SM2.Instance();
		ECPoint userKey = sm2.ecc_curve.decodePoint(publicKey);
		
		ECPoint c0 = cipher.Init_enc(sm2, userKey);
		cipher.Encrypt(source);
		byte[] c3 = new byte[32];
		cipher.Dofinal(c3);
		
		String c0hexStr=Util.getHexString(c0.getEncoded());
//		System.out.println("c1hexStr:"+c0hexStr);
//		System.out.println("c1hexStr:"+c0hexStr.substring(2));
//		System.out.println("C1 " + Util.byteToHex(c1.getEncoded()));
//		System.out.println("C2 " + Util.byteToHex(source));
//		System.out.println("C3 " + Util.byteToHex(c3));
		//C1 C2 C3拼装成加密字串
		//银行加密机截取2位04
		byte[] c1=Util.hexToByte(c0hexStr.substring(2));
		byte[] data1=new byte[c1.length+source.length+c3.length];
		System.arraycopy(c1, 0, data1, 0, c1.length);
		System.arraycopy(c3, 0, data1, c1.length, c3.length);
		System.arraycopy(source, 0, data1, c1.length+c3.length, source.length);
		
		BASE64Encoder encoder = new BASE64Encoder();
		String base64Str = encoder.encode(data1);
		//System.out.println("encode Base64密文---:"+base64Str);
		//System.out.println("---:"+Util.byteToHex(data1));
		return base64Str;
		//return Util.byteToHex(c1.getEncoded()).substring(2) + Util.byteToHex(c3)+Util.byteToHex(source);
	}
	
	//数据解密16进制字符串
	public static byte[] decryptBase64(byte[] privateKey, String encryptedDataBase64) throws IOException{
				
		     BASE64Decoder decoder = new BASE64Decoder();
		     byte[] bytes = decoder.decodeBuffer(encryptedDataBase64);
		     String encryptedData= Util.byteToHex(bytes);
		     System.out.println("sm2 encryptedData--"+encryptedData);
		     //补全04
		     return decryptString(privateKey, "04"+encryptedData);
		}
	
	//数据解密16进制字符串
	public static byte[] decryptString(byte[] privateKey, String encryptedData) throws IOException{
			String c1=encryptedData.substring(0,130);
			String c3=encryptedData.substring(130,130+64);
			String c2=encryptedData.substring(130+64);
			encryptedData=c1+c2+c3;
			return decrypt(privateKey, Util.hexToByte(encryptedData));
	}
	
	//数据解密
	public static byte[] decrypt(byte[] privateKey, byte[] encryptedData) throws IOException
	{
		if (privateKey == null || privateKey.length == 0)
		{
			return null;
		}
		
		if (encryptedData == null || encryptedData.length == 0)
		{
			return null;
		}
		//加密字节数组转换为十六进制的字符串 长度变为encryptedData.length * 2
		String data = Util.byteToHex(encryptedData);
		/***分解加密字串
		 * （C1 = C1标志位2位 + C1实体部分128位 = 130）
		 * （C3 = C3实体部分64位  = 64）
		 * （C2 = encryptedData.length * 2 - C1长度  - C2长度）
		 */
		byte[] c1Bytes = Util.hexToByte(data.substring(0,130));
		int c2Len = encryptedData.length - 97;
		byte[] c3 = Util.hexToByte(data.substring(130 + 2 * c2Len,194 + 2 * c2Len));
		
		byte[] c2 = Util.hexToByte(data.substring(130,130 + 2 * c2Len));
		
		SM2 sm2 = SM2.Instance();
		BigInteger userD = new BigInteger(1, privateKey);
		
		//通过C1实体字节来生成ECPoint
		ECPoint c1 = sm2.ecc_curve.decodePoint(c1Bytes);
		Cipher cipher = new Cipher();
		cipher.Init_dec(userD, c1);
		cipher.Decrypt(c2);
		cipher.Dofinal(c3);
		
		//返回解密结果
		return c2;
	}
	
	
	public static void main(String[] args) throws Exception 
	{
		//生成密钥对
		generateKeyPair();
//		System.out.println("sm2算法验证");
////		String plainText = "ererfeiisgod";
////		System.out.println("明文:"+plainText);
////		byte[] sourceData = plainText.getBytes();
//		
		String prik = "00B9AB0B828FF68872F21A837FC303668428DEA11DCD1B24429D0C99E24EED83D5";
		String pubk ="04B9C9A6E04E9C91F7BA880429273747D7EF5DDEB0BB2FF6317EB00BEF331A83081A6994B8993F3F5D6EADDDB81872266C87C018FB4162F5AF347B483E24620207";
//				 
//		
		System.out.println("国密规范公钥16进制明文:"+pubk);
		System.out.println("国密规范私钥16进制明文:"+prik);
//		
//		//System.out.println("加密: ");
//		//String cipherText = SM2Utils.encrypt(Util.hexToByte(pubk), sourceData);
		String cipherText="EHY7Z+gDnFZYUWrvBxnLF19Hw6RJz6/QCix7gIfMK9WXJ3gpCTMwWdzvKiWH1exH7coYzQYcA85ZIgqPV4CLdQt3OCxU1Ci2ecSGjj/FkpqxtXB4HfbrejSrBCoHGaREvMPe8YNbopXyvGqQXIk7BA==";
		System.out.println("原始密文:"+cipherText);
		String plainText = Util.getHexString(SM2Utils.decryptBase64(Util.hexToByte(prik), cipherText)); //new String(SM2Utils.decryptBase64(Util.hexToByte(prik), cipherText));
		System.out.println("私钥解密: "+plainText);
		
	}
}
