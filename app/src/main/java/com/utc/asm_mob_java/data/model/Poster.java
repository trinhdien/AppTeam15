package com.utc.asm_mob_java.data.model;

import com.google.gson.annotations.Expose;

public class Poster {
    @Expose
    private Integer src;
    @Expose
    private String title;
    @Expose
    private String description;
    @Expose
    private boolean isShow;

    public Poster(Integer src, String title, String description) {
        this.src = src;
        this.title = title;
        this.description = description;
    }

    public Poster(Integer src, String title, String description, boolean isShow) {
        this.src = src;
        this.title = title;
        this.description = description;
        this.isShow = isShow;
    }

    public Integer getSrc() {
        return src;
    }

    public void setSrc(Integer src) {
        this.src = src;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isShow() {
        return isShow;
    }

    public void setShow(boolean show) {
        isShow = show;
    }
}
