package com.github.isacwq.soccernews.data.remote;

import com.github.isacwq.soccernews.domain.News;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface SoccerNewsService {
    @GET("news.json")
    Call<List<News>> getNews();
}
