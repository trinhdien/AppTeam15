package com.utc.asm_mob_java.data.model;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;

import java.util.ArrayList;

public class District {
    @Expose
    private String name;
    @Expose
    private int code;
    @Expose
    private String division_type;
    @Expose
    private String codename;
    @Expose
    private int province_code;
    @Expose
    private ArrayList<Ward> wards;

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

    public int getProvince_code() {
        return province_code;
    }

    public void setProvince_code(int province_code) {
        this.province_code = province_code;
    }

    public ArrayList<Ward> getWards() {
        return wards;
    }

    public void setWards(ArrayList<Ward> wards) {
        this.wards = wards;
    }

    @NonNull
    @Override
    public String toString() {
        return name;
    }
}
