package com.github.isacwq.soccernews.ui.news;

import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.github.isacwq.soccernews.data.local.SoccerNewsRepository;
import com.github.isacwq.soccernews.data.remote.NewsRemoteDataSource;
import com.github.isacwq.soccernews.data.remote.SoccerNewsService;
import com.github.isacwq.soccernews.domain.News;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsViewModel extends ViewModel {

    public enum State {
        LOADING,
        SUCCESS,
        ERROR,
    }

    private final MutableLiveData<List<News>> news = new MutableLiveData<>();
    private final MutableLiveData<State> state = new MutableLiveData<>();

    private SoccerNewsService soccerNewsService;

    public NewsViewModel() {
        soccerNewsService = new NewsRemoteDataSource().newsApi();

        findNews();
    }

    public void findNews() {
        state.setValue(State.LOADING);
        soccerNewsService.getNews().enqueue(new Callback<List<News>>() {
            @Override
            public void onResponse(@NonNull Call<List<News>> call, @NonNull Response<List<News>> response) {
                if (response.isSuccessful()) {
                    news.setValue(response.body());
                    state.setValue(State.SUCCESS);
                } else {
                    state.setValue(State.ERROR);
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<News>> call, @NonNull Throwable t) {
                state.setValue(State.ERROR);
            }
        });
    }

    public void insertNews(News news) {
        AsyncTask.execute(() ->
                SoccerNewsRepository.getInstance().getLocalDb().newsDao().insert(news));
    }

    public LiveData<List<News>> getNews() {
        return news;
    }

    public LiveData<State> getState() {
        return state;
    }
}