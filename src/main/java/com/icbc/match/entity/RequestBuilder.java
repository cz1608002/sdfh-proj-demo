package com.icbc.match.entity;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RequestBuilder {
    private String apiname;
    private String reqdata;
    private String apiurl;
    private String apigw_appid;
    private String priKey;
    //private String pubKey;

    private String requestKey = "biz_content";
    private boolean base64 = false;

    private List<Property> list = new ArrayList<Property>();;

    public RequestBuilder(String apiurl, String apigw_appid, String prikey) throws IOException {
        this.apiurl = apiurl;
        this.apigw_appid = apigw_appid;
        this.priKey = prikey;
    }
    public RequestBuilder apiname(String apiname){
        this.apiname = apiname;
        return this;
    }

    public RequestBuilder data(String data) throws IOException{
        this.reqdata  = data;
        return this;
    }


    public APIGWRequest build(){
        APIEntity ae = new APIEntity();
        ae.setApigw_appid(apigw_appid);
        ae.setApiname(apiname);
        ae.setApiurl(apiurl);
        ae.setBase64(base64);
        ae.setField(requestKey);
        ae.setPriKey(priKey);
        //ae.setPubKey(pubKey);
        ae.setReqdata(reqdata);
        ae.setProperties(list);

        return new APIGWRequest(ae);
    }
}

