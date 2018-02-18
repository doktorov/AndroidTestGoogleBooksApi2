package ru.doktorov.testapp.source.local;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import ru.doktorov.testapp.source.model.Favorites;

@Database(entities = {Favorites.class}, version = 1)
public abstract class FavoritesDatabase extends RoomDatabase {
    public static final String DBName = "Favorites.db";

    public abstract FavoritesDao taskDao();
}
