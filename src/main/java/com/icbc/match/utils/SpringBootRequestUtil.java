package com.icbc.match.utils;

import com.icbc.match.entry.Result;
import org.springframework.http.*;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

public class SpringBootRequestUtil {
    public static String sendPostRequestFormdata(String url, MultiValueMap<String, Object> params) {
        RestTemplate client = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("charset_header", "gb2312");
        HttpMethod method = HttpMethod.POST;
        // 以什么方式提交，自行选择，一般使用json，或者表单
        headers.add("Content-Type", MediaType.APPLICATION_FORM_URLENCODED_VALUE);
        //将请求头部和参数合成一个请求
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(params, headers);
        //执行HTTP请求，将返回的结构使用Response类格式化
        ResponseEntity<String> result = client.exchange(url, method, requestEntity, String.class);


        return result.getBody();
    }


    public static String sendPostRequestByString(String url, MultiValueMap<String, Object> params) {
        RestTemplate client = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        HttpMethod method = HttpMethod.POST;
        // 以什么方式提交，自行选择，一般使用json，或者表单
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        //将请求头部和参数合成一个请求
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(params, headers);
        //执行HTTP请求，将返回的结构使用Response类格式化
        ResponseEntity<String> result = client.exchange(url, method, requestEntity, String.class);
        return result.getBody();
    }


    public static Result sendPostRequest(String url, MultiValueMap<String, Object> params) {
        RestTemplate client = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        HttpMethod method = HttpMethod.POST;
        // 以什么方式提交，自行选择，一般使用json，或者表单
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        //将请求头部和参数合成一个请求
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(params, headers);
        //执行HTTP请求，将返回的结构使用Response类格式化
        ResponseEntity<Result> result = client.exchange(url, method, requestEntity, Result.class);
        return result.getBody();
    }

    public static Result sendGetRequest(String url, MultiValueMap<String, Object> params) {
        RestTemplate restTemplate = new RestTemplate();
        Result result = restTemplate.getForObject(url + "?name={name}", Result.class, params);
        return result;
    }

    public static Result sendGetRequest(String url) {
        RestTemplate restTemplate = new RestTemplate();
        Result result = restTemplate.getForObject(url, Result.class);
        return result;
    }
}
