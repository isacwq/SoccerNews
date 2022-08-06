package com.github.isactomaz.soccernews.ui.adapter;

import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.github.isactomaz.soccernews.databinding.NewsItemBinding;
import com.github.isactomaz.soccernews.domain.News;
import com.squareup.picasso.Picasso;

import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {

    private final List<News> news;

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
        }
    }

    public NewsAdapter(List<News> news) {
        this.news = news;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        NewsItemBinding view = NewsItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bindItems(news.get(position));
    }

    @Override
    public int getItemCount() {
        return news.size();
    }
}
