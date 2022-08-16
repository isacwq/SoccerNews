package com.github.isacwq.soccernews.data.local;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.github.isacwq.soccernews.domain.News;

import java.util.List;

@Dao
public interface NewsDao {
    @Query("SELECT * FROM news")
    List<News> getAll();

    @Query("SELECT * FROM news WHERE favorite = 1")
    LiveData<List<News>> filterFavoritesNews();

    @Query("SELECT * FROM news WHERE title LIKE :title LIMIT 1")
    News findByTitle(String title);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(News news);

    @Delete
    void delete(News news);
}
