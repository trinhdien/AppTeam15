package com.utc.asm_mob_java.data.source.request;

import com.google.gson.annotations.Expose;

public class NewsRequest {
    @Expose
    private String userId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
