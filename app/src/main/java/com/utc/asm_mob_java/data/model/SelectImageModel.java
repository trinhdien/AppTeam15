package com.utc.asm_mob_java.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SelectImageModel {
    @Expose
    @SerializedName("ROOM_NEWS_ID")
    private String newsId;
    @Expose
    @SerializedName("IMAGE_URL")
    private String url;

    public String getNewsId() {
        return newsId;
    }

    public void setNewsId(String newsId) {
        this.newsId = newsId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
