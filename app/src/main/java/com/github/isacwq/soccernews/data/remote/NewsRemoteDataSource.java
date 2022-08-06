package com.github.isacwq.soccernews.data.remote;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NewsRemoteDataSource {
    private final static String BASEURL = "https://digitalinnovationone.github.io/soccer-news-api/";

    private final Retrofit retrofit;

    public NewsRemoteDataSource() {
        retrofit = new Retrofit.Builder()
                .baseUrl(BASEURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public SoccerNewsService newsApi() {
        return retrofit.create(SoccerNewsService.class);
    }
}
