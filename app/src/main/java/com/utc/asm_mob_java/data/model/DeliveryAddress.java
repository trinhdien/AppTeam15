package com.utc.asm_mob_java.data.model;

import com.google.gson.annotations.Expose;

public class DeliveryAddress {
    @Expose
    private Province province;
    @Expose
    private District district;
    @Expose
    private Ward ward;
    @Expose
    private String address;
    @Expose
    private boolean isDefault;
    @Expose
    private String addressDetail;
    @Expose
    private String phoneNumber;
    @Expose
    private String name;
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

    public String getAddressDetail() {
        return addressDetail;
    }

    public void setAddressDetail(String addressDetail) {
        this.addressDetail = addressDetail;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Province getProvince() {
        return province;
    }

    public void setProvince(Province province) {
        this.province = province;
    }

    public District getDistrict() {
        return district;
    }

    public void setDistrict(District district) {
        this.district = district;
    }

    public Ward getWard() {
        return ward;
    }

    public void setWard(Ward ward) {
        this.ward = ward;
    }
}
