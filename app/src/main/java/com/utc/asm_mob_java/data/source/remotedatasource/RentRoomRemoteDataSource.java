package com.utc.asm_mob_java.data.source.remotedatasource;

import com.utc.asm_mob_java.data.source.remotedatasourceimpl.IRentRoomRemoteDataSource;
import com.utc.asm_mob_java.data.source.request.NewsRequest;
import com.utc.asm_mob_java.data.source.response.NewsResponse;
import com.utc.asm_mob_java.data.source.service.RentRoomApi;
import com.utc.asm_mob_java.data.source.service.RequestHelper;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;

public class RentRoomRemoteDataSource implements IRentRoomRemoteDataSource {
    private static RentRoomRemoteDataSource instance = null;
    private final RentRoomApi rentRoomApi;

    private RentRoomRemoteDataSource() {
        rentRoomApi = RequestHelper.getApiServiceAddress();
    }

    public static RentRoomRemoteDataSource newInstance() {
        if (instance == null) {
            instance = new RentRoomRemoteDataSource();
        }
        return instance;
    }

    @Override
    public Observable<NewsResponse> getNewsByUserid(NewsRequest request) {
        return rentRoomApi.getNewsByUserid(request);
    }

    @Override
    public Observable<NewsResponse> getDetailNews(NewsRequest request) {
        return rentRoomApi.getDetailNews(request);
    }
}
