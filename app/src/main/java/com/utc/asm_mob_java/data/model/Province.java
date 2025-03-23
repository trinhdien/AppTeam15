package com.utc.asm_mob_java.data.model;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;

import java.util.List;

public class Province {
    @Expose
    private String name;
    @Expose
    private int code;
    @Expose
    private String division_type;
    @Expose
    private String codename;
    @Expose
    private int phone_code;
    @Expose
    private List<District> districts;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getDivision_type() {
        return division_type;
    }

    public void setDivision_type(String division_type) {
        this.division_type = division_type;
    }

    public String getCodename() {
        return codename;
    }

    public void setCodename(String codename) {
        this.codename = codename;
    }

    public int getPhone_code() {
        return phone_code;
    }

    public void setPhone_code(int phone_code) {
        this.phone_code = phone_code;
    }

    public List<District> getDistricts() {
        return districts;
    }

    public void setDistricts(List<District> districts) {
        this.districts = districts;
    }

    @NonNull
    @Override
    public String toString() {
        return name;
    }
}
