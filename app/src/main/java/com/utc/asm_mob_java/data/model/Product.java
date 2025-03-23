package com.utc.asm_mob_java.data.model;

import com.google.gson.annotations.Expose;
import com.utc.asm_mob_java.utils.CommonActivity;

import java.io.Serializable;
import java.util.List;

public class Product implements Serializable {
    @Expose
    private String id;
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
    @Expose
    private String number;
    @Expose
    private String numberBuy;
    @Expose
    private String numberBuyTemp;
    @Expose
    private String priceTemp;
    @Expose
    private String status;
    @Expose
    private List<Option> lstOption;

    public Product() {
    }

    public Product(String id,Integer img, String title, String price, String sold, String description, String number) {
        this.img = img;
        this.title = title;
        this.price = price;
        this.sold = sold;
        this.description = description;
        this.number = number;
        this.id = id;
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

    public String getNumberBuy() {
        return numberBuy;
    }

    public void setNumberBuy(String numberBuy) {
        this.numberBuy = numberBuy;
    }

    public String getNumberBuyTemp() {
        return CommonActivity.isNullOrEmpty(numberBuyTemp) ?  "1" : numberBuyTemp;
    }

    public void setNumberBuyTemp(String numberBuyTemp) {
        this.numberBuyTemp = numberBuyTemp;
    }

    public String getPriceTemp() {
        return priceTemp == null ? price : priceTemp;
    }

    public void setPriceTemp(String priceTemp) {
        this.priceTemp = priceTemp;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Option> getLstOption() {
        return lstOption;
    }

    public void setLstOption(List<Option> lstOption) {
        this.lstOption = lstOption;
    }
}
