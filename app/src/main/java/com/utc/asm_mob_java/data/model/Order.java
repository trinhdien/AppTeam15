package com.utc.asm_mob_java.data.model;

import com.google.gson.annotations.Expose;

public class Order {
    @Expose
    private Product product;
    @Expose
    private String dateTime;
    @Expose
    private String status;
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
}
