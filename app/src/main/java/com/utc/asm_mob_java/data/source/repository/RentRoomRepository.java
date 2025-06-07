package com.utc.asm_mob_java.data.source.repository;


import com.utc.asm_mob_java.data.source.remotedatasource.RentRoomRemoteDataSource;
import com.utc.asm_mob_java.data.source.remotedatasourceimpl.IRentRoomRemoteDataSource;
import com.utc.asm_mob_java.data.source.request.NewsRequest;
import com.utc.asm_mob_java.data.source.response.NewsResponse;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;

public class RentRoomRepository implements IRentRoomRemoteDataSource {
    private static RentRoomRepository instance;
    private final RentRoomRemoteDataSource mRemote;

    private RentRoomRepository(RentRoomRemoteDataSource mRemote) {
        this.mRemote = mRemote;
    }

    public static RentRoomRepository newInstance() {
        if (instance == null) {
            instance = new RentRoomRepository(RentRoomRemoteDataSource.newInstance());
        }
        return instance;
    }

    @Override
    public Observable<NewsResponse> getAllNews(NewsRequest request) {
        return mRemote.getAllNews(request);
    }

    @Override
    public Observable<NewsResponse> getDetailNews(NewsRequest request) {
        return mRemote.getDetailNews(request);
    }

    @Override
    public Observable<NewsResponse> addNews(NewsRequest request) {
        return mRemote.addNews(request);
    }
}
