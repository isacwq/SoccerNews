package com.github.isactomaz.soccernews.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.github.isactomaz.soccernews.data.local.NewsDao;
import com.github.isactomaz.soccernews.domain.News;

@Database(entities = {News.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract NewsDao newsDao();
}
