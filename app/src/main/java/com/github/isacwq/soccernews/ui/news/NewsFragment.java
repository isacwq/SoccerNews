package com.github.isacwq.soccernews.ui.news;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.room.Room;

import com.github.isacwq.soccernews.database.AppDatabase;
import com.github.isacwq.soccernews.databinding.FragmentNewsBinding;
import com.github.isacwq.soccernews.ui.adapter.NewsAdapter;

public class NewsFragment extends Fragment {

    private FragmentNewsBinding binding;
    private AppDatabase db;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        NewsViewModel newsViewModel =
                new ViewModelProvider(this).get(NewsViewModel.class);

        binding = FragmentNewsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        db = Room.databaseBuilder(getContext(),
                AppDatabase.class, "database-soccer-news").build();

        binding.recyclerNews.setLayoutManager(new LinearLayoutManager(getContext()));
        newsViewModel.getNews().observe(getViewLifecycleOwner(), news -> {
            binding.recyclerNews.setAdapter(new NewsAdapter(news, favoritesNews -> {
                Log.d("button", "like");
                AsyncTask.execute(() -> db.newsDao().insert(favoritesNews));
            }));
        });
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}