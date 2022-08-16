package com.github.isactomaz.soccernews.data.local;

import androidx.room.Room;

import com.github.isactomaz.soccernews.App;
import com.github.isactomaz.soccernews.database.AppDatabase;

public class SoccerNewsRepository {
    private static final String LOCAL_DB_NAME = "database-soccer-news";

    private AppDatabase soccerNewsDb;

    private SoccerNewsRepository() {
        soccerNewsDb = Room.databaseBuilder(
                App.getInstance(),
                AppDatabase.class,
                LOCAL_DB_NAME
        ).build();
    }

    public AppDatabase getLocalDb() {
        return soccerNewsDb;
    }

    public static SoccerNewsRepository getInstance() {
        return LazyHolder.INSTANCE;
    }

    private static class LazyHolder {
        private static final SoccerNewsRepository INSTANCE = new SoccerNewsRepository();
    }
}
