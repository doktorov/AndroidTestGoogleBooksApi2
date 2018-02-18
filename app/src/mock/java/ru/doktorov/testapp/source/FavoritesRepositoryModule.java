package ru.doktorov.testapp.source;

import android.app.Application;
import android.arch.persistence.room.Room;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import ru.doktorov.testapp.source.local.FavoritesDao;
import ru.doktorov.testapp.source.local.FavoritesDatabase;
import ru.doktorov.testapp.source.local.FavoritesLocalDataSource;
import ru.doktorov.testapp.util.AppExecutors;
import ru.doktorov.testapp.util.DiskIOThreadExecutor;

@Module
abstract public class FavoritesRepositoryModule {

    @Singleton
    @Binds
    @Local
    abstract FavoritesDataSource provideFavoritesLocalDataSource(FavoritesLocalDataSource dataSource);

    @Singleton
    @Provides
    static FavoritesDatabase provideDb(Application context) {
        return Room.databaseBuilder(context.getApplicationContext(), FavoritesDatabase.class, FavoritesDatabase.DBName)
                .build();
    }

    @Singleton
    @Provides
    static FavoritesDao provideFavoritesDao(FavoritesDatabase db) {
        return db.taskDao();
    }

    @Singleton
    @Provides
    static AppExecutors provideAppExecutors() {
        return new AppExecutors(new DiskIOThreadExecutor());
    }
}
