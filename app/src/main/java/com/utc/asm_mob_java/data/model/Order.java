package com.utc.asm_mob_java.data.model;

import android.graphics.Color;

import com.google.gson.annotations.Expose;

public class Order {
    @Expose
    private Product product;
    @Expose
    private String dateTime;
    @Expose
    private String status;
    @Expose
    private String colorCode;
    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getColorCode() {
        return colorCode;
    }

    public void setColorCode(String colorCode) {
        this.colorCode = colorCode;
    }

    public int getColor(){
        if ("1".equals(colorCode)){
            return Color.parseColor("#00504B");
        }else {
            return Color.parseColor("#76BB68");
        }
    }

}
