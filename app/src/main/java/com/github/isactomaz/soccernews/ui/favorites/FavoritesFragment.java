package com.github.isactomaz.soccernews.ui.favorites;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.github.isactomaz.soccernews.MainActivity;
import com.github.isactomaz.soccernews.databinding.FragmentFavoritesBinding;
import com.github.isactomaz.soccernews.domain.News;
import com.github.isactomaz.soccernews.ui.adapter.NewsAdapter;

import java.util.List;

public class FavoritesFragment extends Fragment {

    private FragmentFavoritesBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        FavoritesViewModel favoritesViewModel =
                new ViewModelProvider(this).get(FavoritesViewModel.class);

        binding = FragmentFavoritesBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        getFavoritesNews();

        return root;
    }

    private void getFavoritesNews() {
        MainActivity activity = (MainActivity) getActivity();
        if (activity != null) {
            List<News> favoritesNews = activity.getDb().newsDao().filterFavoritesNews();
            binding.recyclerFavorites.setLayoutManager(new LinearLayoutManager(getContext()));
            binding.recyclerFavorites.setAdapter(new NewsAdapter(favoritesNews, updatedNews -> {
                activity.getDb().newsDao().insert(updatedNews);
            }));
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}