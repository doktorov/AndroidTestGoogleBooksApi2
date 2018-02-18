package ru.doktorov.testapp.main;

import ru.doktorov.testapp.main.favoritebook.FavoriteBookContract;
import ru.doktorov.testapp.main.favoritebook.FavoriteBookFragment;
import ru.doktorov.testapp.main.favoritebook.FavoriteBookPresenter;
import ru.doktorov.testapp.main.searchbook.SearchBookContract;
import ru.doktorov.testapp.main.searchbook.SearchBookFragment;
import ru.doktorov.testapp.main.searchbook.SearchBookPresenter;
import ru.doktorov.testapp.di.FragmentScoped;

import dagger.Binds;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class MainModule {
    @FragmentScoped
    @ContributesAndroidInjector
    abstract SearchBookFragment searchBookFragment();

    @FragmentScoped
    @Binds
    abstract SearchBookContract.Presenter searchBookPresenter(SearchBookPresenter presenter);

    @FragmentScoped
    @ContributesAndroidInjector
    abstract FavoriteBookFragment favoriteBookFragment();

    @FragmentScoped
    @Binds
    abstract FavoriteBookContract.Presenter favoriteBookContract(FavoriteBookPresenter presenter);
}
