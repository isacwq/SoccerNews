package com.github.isacwq.soccernews.ui.favorites;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.github.isacwq.soccernews.databinding.FragmentFavoritesBinding;
import com.github.isacwq.soccernews.ui.adapter.NewsAdapter;

public class FavoritesFragment extends Fragment {

    private FragmentFavoritesBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        FavoritesViewModel favoritesViewModel =
                new ViewModelProvider(this).get(FavoritesViewModel.class);

        binding = FragmentFavoritesBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        getFavoritesNews(favoritesViewModel);

        return root;
    }

    private void getFavoritesNews(FavoritesViewModel favoritesViewModel) {
        favoritesViewModel.getFavoritesNews().observe(getViewLifecycleOwner(), favoritesNews -> {
            binding.recyclerFavorites.setLayoutManager(new LinearLayoutManager(getContext()));
            binding.recyclerFavorites.setAdapter(
                    new NewsAdapter(favoritesNews, favoritesViewModel::insertNews)
            );
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}