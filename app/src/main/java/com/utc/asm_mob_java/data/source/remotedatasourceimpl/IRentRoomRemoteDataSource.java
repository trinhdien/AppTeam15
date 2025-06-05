package com.utc.asm_mob_java.data.source.remotedatasourceimpl;

import com.utc.asm_mob_java.data.source.request.NewsRequest;
import com.utc.asm_mob_java.data.source.response.NewsResponse;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;

public interface IRentRoomRemoteDataSource {
    Observable<NewsResponse> getNewsByUserid(NewsRequest request);
    Observable<NewsResponse> getDetailNews(NewsRequest request);
}
