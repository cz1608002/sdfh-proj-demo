package com.icbc.match.entity;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;

import java.util.List;
@Data
public class apigwrspData {



    private JSONObject data;
    private JSONObject merid;
    private JSONObject fseqno;
    private JSONObject filed1;
    private JSONObject filed2;
    private JSONObject filed3;
    private List<accountList> accountlist;

    @Data
    class accountList{
      private String accountcode;

    }




}