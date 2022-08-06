package com.github.isacwq.soccernews.ui.news;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.github.isacwq.soccernews.data.remote.NewsRemoteDataSource;
import com.github.isacwq.soccernews.data.remote.SoccerNewsService;
import com.github.isacwq.soccernews.domain.News;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsViewModel extends ViewModel {

    private final MutableLiveData<List<News>> news = new MutableLiveData<>();

    public NewsViewModel() {
        SoccerNewsService soccerNewsService = new NewsRemoteDataSource().newsApi();

        soccerNewsService.getNews().enqueue(new Callback<List<News>>() {
            @Override
            public void onResponse(@NonNull Call<List<News>> call, @NonNull Response<List<News>> response) {
                if (response.isSuccessful()) {
                    news.setValue(response.body());
                } else {
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<News>> call, @NonNull Throwable t) {
            }
        });
    }

    public LiveData<List<News>> getNews() {
        return news;
    }
}