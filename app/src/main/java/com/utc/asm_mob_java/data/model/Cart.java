package com.utc.asm_mob_java.data.model;

import com.google.gson.annotations.Expose;

public class Cart {
    @Expose
    private Product product;
    @Expose
    private boolean isChoose;

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public boolean isChoose() {
        return isChoose;
    }

    public void setChoose(boolean choose) {
        isChoose = choose;
    }
}
