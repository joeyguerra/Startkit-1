package com.thoughtworks.android.startkit.retrofit;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

/**
 * Created by zhuang on 12/03/2017.
 */

public interface DouBanService {

    @GET("/v2/book/search")
    Call<DouBanResponseData> getBooks(@QueryMap Map<String, String> options);
}
