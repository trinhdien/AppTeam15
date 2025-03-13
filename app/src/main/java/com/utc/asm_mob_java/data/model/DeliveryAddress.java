package com.utc.asm_mob_java.data.model;

import com.google.gson.annotations.Expose;

public class DeliveryAddress {
    @Expose
    private String address;
    @Expose
    private boolean isDefault;

    public DeliveryAddress() {
    }

    public DeliveryAddress(String address, boolean isDefault) {
        this.address = address;
        this.isDefault = isDefault;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public boolean isDefault() {
        return isDefault;
    }

    public void setDefault(boolean aDefault) {
        isDefault = aDefault;
    }
}
