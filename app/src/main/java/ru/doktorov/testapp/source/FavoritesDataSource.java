package ru.doktorov.testapp.source;

import android.support.annotation.NonNull;

import io.reactivex.Flowable;
import ru.doktorov.testapp.source.model.Favorites;

import java.util.List;

public interface FavoritesDataSource {
    void saveFavorite(@NonNull Favorites favorite);

    Flowable<List<Favorites>> getFavorites();

    void deleteFavorite(Favorites favorite);
}
