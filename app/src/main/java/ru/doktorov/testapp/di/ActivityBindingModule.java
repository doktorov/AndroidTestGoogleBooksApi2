package ru.doktorov.testapp.di;

import ru.doktorov.testapp.main.MainActivity;
import ru.doktorov.testapp.main.MainModule;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
abstract class ActivityBindingModule {
    @ActivityScoped
    @ContributesAndroidInjector(modules = MainModule.class)
    abstract MainActivity mainActivity();
}
