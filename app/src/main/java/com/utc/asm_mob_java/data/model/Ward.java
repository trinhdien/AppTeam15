package com.utc.asm_mob_java.data.model;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;

public class Ward {
    @Expose
    private String name;
    @Expose
    private int code;
    @Expose
    private String division_type;
    @Expose
    private String codename;
    @Expose
    private int district_code;

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

    public int getDistrict_code() {
        return district_code;
    }

    public void setDistrict_code(int district_code) {
        this.district_code = district_code;
    }

    @NonNull
    @Override
    public String toString() {
        return name;
    }
}
