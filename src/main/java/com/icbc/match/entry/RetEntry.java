package com.icbc.match.entry;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class RetEntry {

    private String retCode;
    private String retMsg;

    private Map<String, Object> params;
    

    public RetEntry() {

    }

    public RetEntry(String retCode, String retMsg) {
        this.retCode = retCode;
        this.retMsg = retMsg;
    }

    public String getRetCode() {
        return retCode;
    }

    public void setRetCode(String retCode) {
        this.retCode = retCode;
    }

    public String getRetMsg() {
        return retMsg;
    }

    public void setRetMsg(String retMsg) {
        this.retMsg = retMsg;
    }

    public RetEntry addParam(String key, Object value) {

        if (params == null) {
            params = new HashMap<>();
        }

        params.put(key, value);

        return this;
    }
    
    public Object getParam(String key) {
        return params.get(key);
    }

    public boolean isRetOk() {
        return "0".equals(retCode);
    }

    public boolean isRetNotOk() {
        return !"0".equals(retCode);
    }

    public static RetEntry getOneOkRetEntry() {
        return new RetEntry("0", "");
    }

    @Override
    public String toString() {
    	try{
    		JSONObject json = new JSONObject();
            json.put("retCode", retCode);
            json.put("retMsg", retMsg);

            if (params == null) {
                return json.toString();
            }

            Set<String> keys = params.keySet();

            for (String key : keys) {
                json.put(key, params.get(key));
            }
            
            return json.toString();
    	}catch(Exception e){
    		return "";
    	}
        
    }
}
