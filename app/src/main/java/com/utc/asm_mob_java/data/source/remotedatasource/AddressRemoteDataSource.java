package com.utc.asm_mob_java.data.source.remotedatasource;

import com.utc.asm_mob_java.data.model.District;
import com.utc.asm_mob_java.data.model.Province;
import com.utc.asm_mob_java.data.model.Ward;
import com.utc.asm_mob_java.data.source.remotedatasourceimpl.IAddressRemoteDataSource;
import com.utc.asm_mob_java.data.source.service.RequestHelper;
import com.utc.asm_mob_java.data.source.service.ShopBearApi;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;

public class AddressRemoteDataSource implements IAddressRemoteDataSource {
    private static AddressRemoteDataSource instance = null;
    private final ShopBearApi shopBearApi;

    private AddressRemoteDataSource() {
        shopBearApi = RequestHelper.getApiServiceAddress();
    }

    public static AddressRemoteDataSource newInstance() {
        if (instance == null) {
            instance = new AddressRemoteDataSource();
        }
        return instance;
    }

    @Override
    public Observable<List<Province>> getProvinces() {
        return shopBearApi.getProvinces();
    }

    @Override
    public Observable<List<District>> getDistricts() {
        return shopBearApi.getDistricts();
    }

    @Override
    public Observable<List<Ward>> getWards() {
        return shopBearApi.getWards();
    }
}
