package com.utc.asm_mob_java.data.model;

import com.google.gson.annotations.Expose;

import java.io.Serializable;

public class Product implements Serializable {
    @Expose
    private Integer img;
    @Expose
    private String title;
    @Expose
    private String price;
    @Expose
    private String sold;
    @Expose
    private String description;
    @Expose String number;

    public Product() {
    }

    public Product(Integer img, String title, String price, String sold, String description) {
        this.img = img;
        this.title = title;
        this.price = price;
        this.sold = sold;
        this.description = description;
    }

    public Integer getImg() {
        return img;
    }

    public void setImg(Integer img) {
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

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
