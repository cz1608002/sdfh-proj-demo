package com.icbc.match.entity;

import lombok.Data;

@Data
public class KeyV {

    private String key;
    private String value;

    public KeyV(String key, String value) {
        this.key = key;
        this.value = value;
    }
}
