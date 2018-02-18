package ru.doktorov.testapp.source.local;

import android.support.annotation.NonNull;

import io.reactivex.Flowable;
import ru.doktorov.testapp.source.model.Favorites;
import ru.doktorov.testapp.source.FavoritesDataSource;
import ru.doktorov.testapp.util.AppExecutors;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import static com.google.common.base.Preconditions.checkNotNull;

@Singleton
public class FavoritesLocalDataSource implements FavoritesDataSource {

    private final FavoritesDao mFavoritesDao;

    private final AppExecutors mAppExecutors;

    @Inject
    FavoritesLocalDataSource(@NonNull AppExecutors executors, @NonNull FavoritesDao favoritesDao) {
        mFavoritesDao = favoritesDao;
        mAppExecutors = executors;
    }

    @Override
    public void saveFavorite(@NonNull final Favorites favorite) {
        Runnable saveRunnable = () ->
                mFavoritesDao.insertFavorite(favorite);

        mAppExecutors.diskIO().execute(saveRunnable);
    }

    @Override
    public Flowable<List<Favorites>> getFavorites() {
        return mFavoritesDao.getFavorites();
    }

    @Override
    public void deleteFavorite(@NonNull Favorites favorite) {
        Runnable deleteRunnable = () ->
                mFavoritesDao.deleteFavorite(favorite);

        mAppExecutors.diskIO().execute(deleteRunnable);
    }
}
