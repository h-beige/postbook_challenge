package com.example.postbookchallenge.domain.service;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import javax.inject.Inject;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TypicodeApi {
    private final static String BASE_URL = "https://jsonplaceholder.typicode.com/";

    private static Retrofit retrofit = null;

    @Inject
    public TypicodeApi() {
    }

    private Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .baseUrl(BASE_URL)
                    .build();
        }
        return retrofit;
    }

    public ITypicodeService getTypicodeService() {
        return getClient().create(ITypicodeService.class);
    }
}
