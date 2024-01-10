package com.icbc.match.utils;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class YkHttpPost {
    public static String httpPostWithForm(String url, Map<String, String> paramsMap){
        // 用于接收返回的结果
        String resultData ="";
        try {
            HttpPost post = new HttpPost(url);
            List<BasicNameValuePair> pairList = new ArrayList<BasicNameValuePair>();
            // 迭代Map-->取出key,value放到BasicNameValuePair对象中-->添加到list中
            for (String key : paramsMap.keySet()) {
                pairList.add(new BasicNameValuePair(key, paramsMap.get(key)));
            }
            UrlEncodedFormEntity uefe = new UrlEncodedFormEntity(pairList, "utf-8");
            post.setEntity(uefe);
            // 创建一个http客户端
            CloseableHttpClient httpClient = HttpClientBuilder.create().build();
            // 发送post请求
            HttpResponse response = httpClient.execute(post);

            // 状态码为：200
            if(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
                // 返回数据：
                resultData = EntityUtils.toString(response.getEntity(),"UTF-8");
            }else{
                throw new RuntimeException("接口连接失败！");
            }
        } catch (Exception e) {
            throw new RuntimeException("接口连接失败！");
        }
        return resultData;
    }
}
