package ru.doktorov.testapp;

import ru.doktorov.testapp.source.FavoritesRepository;
import ru.doktorov.testapp.di.DaggerAppComponent;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DaggerApplication;

public class TestApplication extends DaggerApplication {
    @Inject
    FavoritesRepository tasksRepository;

    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        return DaggerAppComponent.builder().application(this).build();
    }
}