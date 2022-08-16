package com.github.isacwq.soccernews.ui.favorites;

import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.github.isacwq.soccernews.data.local.SoccerNewsRepository;
import com.github.isacwq.soccernews.domain.News;

import java.util.List;

public class FavoritesViewModel extends ViewModel {

    public FavoritesViewModel() {
    }

    public LiveData<List<News>> getFavoritesNews() {
        return SoccerNewsRepository.getInstance().getLocalDb().newsDao().filterFavoritesNews();
    }

    public void insertNews(News news) {
        AsyncTask.execute(() ->
                SoccerNewsRepository.getInstance().getLocalDb().newsDao().insert(news));
    }
}