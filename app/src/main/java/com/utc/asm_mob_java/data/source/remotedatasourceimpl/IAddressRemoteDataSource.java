package com.utc.asm_mob_java.data.source.remotedatasourceimpl;

import com.utc.asm_mob_java.data.model.District;
import com.utc.asm_mob_java.data.model.Province;
import com.utc.asm_mob_java.data.model.Ward;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;

public interface IAddressRemoteDataSource {
    Observable<List<Province>> getProvinces();

    Observable<List<District>> getDistricts();

    Observable<List<Ward>> getWards();
}
