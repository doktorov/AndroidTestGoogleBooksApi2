package ru.doktorov.testapp.source;

import android.support.annotation.NonNull;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Flowable;
import ru.doktorov.testapp.source.model.Favorites;

@Singleton
public class FavoritesRepository implements FavoritesDataSource {

    private final FavoritesDataSource mTasksLocalDataSource;

    @Inject
    FavoritesRepository(@Local FavoritesDataSource tasksLocalDataSource) {
        mTasksLocalDataSource = tasksLocalDataSource;
    }

    @Override
    public void saveFavorite(@NonNull Favorites favorites) {
        mTasksLocalDataSource.saveFavorite(favorites);
    }

    @Override
    public Flowable<List<Favorites>> getFavorites() {
        return mTasksLocalDataSource.getFavorites();
    }

    @Override
    public void deleteFavorite(Favorites favorite) {
        mTasksLocalDataSource.deleteFavorite(favorite);
    }
}
