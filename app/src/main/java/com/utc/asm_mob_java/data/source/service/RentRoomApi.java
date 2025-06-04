package com.utc.asm_mob_java.data.source.service;

import com.utc.asm_mob_java.data.source.request.NewsRequest;
import com.utc.asm_mob_java.data.source.response.NewsResponse;
import com.utc.asm_mob_java.utils.Config;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface RentRoomApi {
    @POST(Config.BASE_URL +  "getNewsById")
    Observable<NewsResponse> getNewsByUserid(@Body NewsRequest request);
}
