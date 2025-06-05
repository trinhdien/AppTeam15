package com.utc.asm_mob_java.data.model;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;

public class KeyValue {
    @Expose
    private String key;
    @Expose
    private String value;

    public KeyValue() {
    }

    public KeyValue(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @NonNull
    @Override
    public String toString() {
        return value;
    }

}
