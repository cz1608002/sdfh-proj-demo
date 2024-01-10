package com.icbc.match.entity;

import java.util.List;

public class APIEntity {

    public String apigw_appid;
    public String apiname;
    public String apiurl;
    //	public String apigw_appid;
    public String requestKey;
    public String priKey;
    public String list;
    public String Reqdata;
    public String field;
    public List<Property> properties;
    public  boolean base64;
    public boolean isBase64() {
        return base64;
    }
    public void setBase64(boolean base64) {
        this.base64 = base64;
    }




    public List<Property> getProperties() {
        return properties;
    }
    public void setProperties(List<Property> properties) {
        this.properties = properties;
    }








    public String getField() {
        return field;
    }
    public void setField(String field) {
        this.field = field;
    }



    public String getApigw_appid() {
        return apigw_appid;
    }
    public void setApigw_appid(String apigw_appid) {
        this.apigw_appid = apigw_appid;
    }
    public String getApiname() {
        return apiname;
    }
    public void setApiname(String apiname) {
        this.apiname = apiname;
    }
    public String getApiurl() {
        return apiurl;
    }
    public void setApiurl(String apiurl) {
        this.apiurl = apiurl;
    }
    public String getRequestKey() {
        return requestKey;
    }
    public void setRequestKey(String requestKey) {
        this.requestKey = requestKey;
    }
    public String getPriKey() {
        return priKey;
    }
    public void setPriKey(String priKey) {
        this.priKey = priKey;
    }
    public String getList() {
        return list;
    }
    public void setList(String list) {
        this.list = list;
    }
    public String getReqdata() {
        return Reqdata;
    }
    public void setReqdata(String reqdata) {
        this.Reqdata = reqdata;
    }


}
