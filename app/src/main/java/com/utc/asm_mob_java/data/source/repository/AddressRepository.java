package com.utc.asm_mob_java.data.source.repository;


import com.utc.asm_mob_java.data.model.District;
import com.utc.asm_mob_java.data.model.Province;
import com.utc.asm_mob_java.data.model.Ward;
import com.utc.asm_mob_java.data.source.remotedatasource.AddressRemoteDataSource;
import com.utc.asm_mob_java.data.source.remotedatasourceimpl.IAddressRemoteDataSource;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;

public class AddressRepository implements IAddressRemoteDataSource {
    private static AddressRepository instance;
    private final AddressRemoteDataSource mRemote;

    private AddressRepository(AddressRemoteDataSource mRemote) {
        this.mRemote = mRemote;
    }

    public static AddressRepository newInstance() {
        if (instance == null) {
            instance = new AddressRepository(AddressRemoteDataSource.newInstance());
        }
        return instance;
    }

    @Override
    public Observable<List<Province>> getProvinces() {
        return mRemote.getProvinces();
    }

    @Override
    public Observable<List<District>> getDistricts() {
        return mRemote.getDistricts();
    }

    @Override
    public Observable<List<Ward>> getWards() {
        return mRemote.getWards();
    }
}
