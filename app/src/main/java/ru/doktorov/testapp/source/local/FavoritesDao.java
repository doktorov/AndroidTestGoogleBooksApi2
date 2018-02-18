package ru.doktorov.testapp.source.local;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import io.reactivex.Flowable;
import ru.doktorov.testapp.source.model.Favorites;

import java.util.List;

@Dao
public interface FavoritesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertFavorite(Favorites favorites);

    @Query("SELECT * FROM Favorites")
    Flowable<List<Favorites>> getFavorites();

    @Delete
    void deleteFavorite(Favorites favorites);
}
