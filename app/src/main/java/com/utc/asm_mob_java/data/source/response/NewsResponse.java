package com.utc.asm_mob_java.data.source.response;

import com.google.gson.annotations.Expose;
import com.utc.asm_mob_java.data.model.NewsModel;

import java.util.List;

public class NewsResponse {

    @Expose
    private String status;
    @Expose
    private String message;
    @Expose
    private List<NewsModel> object;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<NewsModel> getObject() {
        return object;
    }

    public void setObject(List<NewsModel> object) {
        this.object = object;
    }
}
