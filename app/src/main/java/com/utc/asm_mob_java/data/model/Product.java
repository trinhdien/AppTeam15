package com.utc.asm_mob_java.data.model;

import com.google.gson.annotations.Expose;

import java.io.Serializable;

public class Product implements Serializable {
    @Expose
    private String img;
    @Expose
    private String title;
    @Expose
    private String price;
    @Expose
    private String sold;
    @Expose
    private String description;

    public Product() {
    }

    public Product(String img, String title, String price, String sold) {
        this.img = img;
        this.title = title;
        this.price = price;
        this.sold = sold;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getSold() {
        return sold;
    }

    public void setSold(String sold) {
        this.sold = sold;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
