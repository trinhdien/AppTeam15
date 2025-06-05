package com.utc.asm_mob_java.data.source.request;

import com.google.gson.annotations.Expose;

public class NewsRequest {
    @Expose
    private String userId;
    @Expose
    private String newsId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getNewsId() {
        return newsId;
    }

    public void setNewsId(String newsId) {
        this.newsId = newsId;
    }
}
