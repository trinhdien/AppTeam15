package com.utc.asm_mob_java.data.source.service;

import com.utc.asm_mob_java.data.model.District;
import com.utc.asm_mob_java.data.model.Province;
import com.utc.asm_mob_java.data.model.Ward;
import com.utc.asm_mob_java.utils.Config;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ShopBearApi {
    @GET(Config.URL_ADDRESS + "api/p")
    Observable<List<Province>> getProvinces();

    @GET(Config.URL_ADDRESS + "api/d")
    Observable<List<District>> getDistricts();

    @GET(Config.URL_ADDRESS + "api/w")
    Observable<List<Ward>> getWards();
}
