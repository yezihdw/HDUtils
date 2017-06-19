package com.hdw.hdutils.api;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;

/**
 * Created by hdw on 2017/6/2.
 */

public interface Api {
    @POST
    Observable<String> login(@QueryMap RequestParams params);

    @GET
    Observable<String> register(@QueryMap RequestParams params);

}
