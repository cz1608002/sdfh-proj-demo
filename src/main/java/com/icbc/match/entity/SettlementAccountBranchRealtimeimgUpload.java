package com.icbc.match.entity;

import lombok.Data;

@Data
public class SettlementAccountBranchRealtimeimgUpload {


    private String corpNo;//             true 20 合作方机构编号，我行分配的机构编号 12位数字
    private String trxAccDate;//                 true 10 合作方交易日期 2017-03-02
    private String trxAccTime;//                 true 8 合作方交易时间 10:38:01
    private String corpSerno;//                true 36 合作方交易单号 ABC123456789
    private String outServiceCode;//                     true 20 外部服务代码 uploadimg
    private String certNo;//             true 44 证件号码(支持加密，Base64编码,若身份证号尾部里带有’x’请使用大写X) 身份证号码
    private String custName;//               true 88 户名(支持加密，Base64编码) 张三
    private String validityPeriod;//                     true 10 证件有效期到日期（永久时送9999-12-30） 2017-03-02
    private String frontImgType;//                   true 2 身份证正面图片类型 01-jpg 02-png 03-jpeg 04-bmp
    private String frontImg;//               true 确保整个请求报文大小不超过1M即可，单张图片大小调用方自行调整 身份证正面图片（deflate压缩后base64） NjIyNTc4OTQ1NjE
    private String backImgType;//                  true 2 身份证背面图片类型 01-jpg 02-png 03-jpeg 04-bmp
    private String backImg;//              true 确保整个请求报文大小不超过1M即可，单张图片大小调用方自行调整 身份证背面图片（deflate压缩后base64） NjIyNTc4OTQ1NjE
    private int clearFlag;//        int true 1 是否补上传影像标志 送1是
    private String mediumId;//               false 44 二类户卡号(支持加密，Base64编码),补上传影像必输 6214760200000029162
    private String secretKey;//                false 152 使用SM2公钥加密的sm4对称密钥，使用密文时必输，Base64编码 -
    private String faceImgType;//                  false 2 现场人脸图片类型 01-jpg 02-png 03-jpeg 04-bmp
    private String faceImg;//              false 确保整个请求报文大小不超过1M即可，单张图片大小调用方自行调整 现场人脸图片（deflate压缩后base64） NjIyNTc4OTQ1NjE

}
