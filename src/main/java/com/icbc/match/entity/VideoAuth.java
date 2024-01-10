package com.icbc.match.entity;

import lombok.Data;

@Data
public class VideoAuth {
    private  String reqId;
    private String channel;
    private String name;
    private String idCode;
    private String validStart;
    private String validEnd;
    private String video1;
    private String trxZone;
    private String isUseLabel;
    private String labelByVideo;
    private String tokenByVideo;

}
