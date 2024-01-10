package com.icbc.match.entity;

import lombok.Data;

@Data
public class PersonalProofcheck {
    private String channel;
    private String reqId;
    private String caseId;
    private String imageFile;
}
