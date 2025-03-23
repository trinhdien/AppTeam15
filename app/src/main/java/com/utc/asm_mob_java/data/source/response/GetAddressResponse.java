package com.utc.asm_mob_java.data.source.response;

import com.google.gson.annotations.Expose;
import com.utc.asm_mob_java.data.model.District;
import com.utc.asm_mob_java.data.model.Province;
import com.utc.asm_mob_java.data.model.Ward;

import java.util.List;

public class GetAddressResponse {
    @Expose
    private List<Province> provinces;
    @Expose
    private List<District> districts;
    @Expose
    private List<Ward> wards;

    public GetAddressResponse(List<Province> provinces, List<District> districts, List<Ward> wards) {
        this.provinces = provinces;
        this.districts = districts;
        this.wards = wards;
    }

    public List<Province> getProvinces() {
        return provinces;
    }

    public void setProvinces(List<Province> provinces) {
        this.provinces = provinces;
    }

    public List<District> getDistricts() {
        return districts;
    }

    public void setDistricts(List<District> districts) {
        this.districts = districts;
    }

    public List<Ward> getWards() {
        return wards;
    }

    public void setWards(List<Ward> wards) {
        this.wards = wards;
    }
}
