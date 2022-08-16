package com.github.isactomaz.soccernews.ui.news;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.github.isactomaz.soccernews.R;
import com.github.isactomaz.soccernews.databinding.FragmentNewsBinding;
import com.github.isactomaz.soccernews.ui.adapter.NewsAdapter;
import com.google.android.material.snackbar.Snackbar;

public class NewsFragment extends Fragment {

    private FragmentNewsBinding binding;
    private NewsViewModel newsViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        newsViewModel = new ViewModelProvider(this).get(NewsViewModel.class);

        binding = FragmentNewsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        binding.recyclerNews.setLayoutManager(new LinearLayoutManager(getContext()));
        observeNews();
        setupStates();
        binding.swipeRefreshNews.setOnRefreshListener(newsViewModel::findNews);
        return root;
    }

    private void observeNews() {
        newsViewModel.getNews().observe(getViewLifecycleOwner(), news -> {
            binding.recyclerNews.setAdapter(new NewsAdapter(news, favoritesNews -> {
                Log.d("SOCCER_NEWS", "button like");
                newsViewModel.insertNews(favoritesNews);
            }));
        });
    }

    private void setupStates() {
        newsViewModel.getState().observe(getViewLifecycleOwner(), state -> {
            switch (state) {
                case LOADING:
                    binding.swipeRefreshNews.setRefreshing(true);
                    break;
                case SUCCESS:
                    binding.swipeRefreshNews.setRefreshing(false);
                    break;
                case ERROR:
                    binding.swipeRefreshNews.setRefreshing(false);
                    Snackbar.make(
                            binding.swipeRefreshNews,
                            R.string.error_network,
                            Snackbar.LENGTH_SHORT
                    ).show();
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}