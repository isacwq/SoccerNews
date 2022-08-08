package com.github.isactomaz.soccernews.ui.adapter;

import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.github.isactomaz.soccernews.R;
import com.github.isactomaz.soccernews.databinding.NewsItemBinding;
import com.github.isactomaz.soccernews.domain.News;
import com.squareup.picasso.Picasso;

import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {

    private final List<News> news;
    private final FavoritesListener favoritesListener;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final NewsItemBinding binding;

        public ViewHolder(NewsItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bindItems(News news) {
            binding.textNewsTitle.setText(news.getTitle());
            binding.textPreviewNews.setText(news.getDescription());
            Picasso.get().load(news.getImage()).into(binding.imageNews);
            binding.buttonOpenLink.setOnClickListener(view -> {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(news.getLink()));
                itemView.getContext().startActivity(intent);
            });
            binding.buttonShare.setOnClickListener(view -> {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_TEXT, news.getLink());
                itemView.getContext().startActivity(Intent.createChooser(intent, "Share via"));
            });
        }
    }

    public NewsAdapter(List<News> news, FavoritesListener favoritesListener) {
        this.news = news;
        this.favoritesListener = favoritesListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        NewsItemBinding view = NewsItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        News _news = news.get(position);
        holder.bindItems(_news);
        holder.binding.buttonLike.setOnClickListener(view -> {
            _news.setFavorite(!_news.isFavorite());
            this.favoritesListener.onFavorite(_news);
            notifyItemChanged(position);
            Log.d("NEWS", "is " + _news.isFavorite());
        });

        int iconColor;
        if (_news.isFavorite()) {
            iconColor = R.color.pink_200;
        } else {
            iconColor = R.color.pink_500;
        }
        //holder.binding.buttonLike.setForeground(ContextCompat.getDrawable(holder.itemView.getContext(), R.drawable.ic_news_black_24dp));
        holder.binding.buttonLike.setBackgroundColor(holder.itemView.getContext().getResources().getColor(iconColor));
    }

    @Override
    public int getItemCount() {
        return news.size();
    }

    public interface FavoritesListener {
        void onFavorite(News news);
    }
}
