package com.thoughtworks.android.startkit.retrofit;

import android.os.AsyncTask;

import com.thoughtworks.android.startkit.BookData;
import com.thoughtworks.android.startkit.BookItem;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by zhuang on 12/03/2017.
 */

public class DouBanDataTask extends AsyncTask<Integer, Void, BookData> {
    String DATA_URL = "https://api.douban.com";
    String DATA_TAG = "IT";
    int DATA_PER_PAGE = 20;

    Retrofit mRetrofit;
    DouBanService mService;

    public DouBanDataTask() {
        mRetrofit = new Retrofit.Builder()
                .baseUrl(DATA_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        mService = mRetrofit.create(DouBanService.class);
    }

    @Override
    protected BookData doInBackground(Integer... params) {
        Map<String, String> queryMap = new HashMap<>();
        queryMap.put("tag",DATA_TAG);
        queryMap.put("count",String.valueOf(DATA_PER_PAGE));
        queryMap.put("start",String.valueOf(params[0]));
        try {
            Response<DouBanResponseData> response = mService.getBooks(queryMap).execute();
            DouBanResponseData douBanResponseData = response.body();
            List<BookItem> list = new ArrayList<>();
            for(DouBanResponseData.BooksBean bean : douBanResponseData.getBooks()) {
                list.add(new DouBanBook(bean));
            }
            return new BookData(list,douBanResponseData.getTotal(), douBanResponseData.getCount(), douBanResponseData.getStart());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}