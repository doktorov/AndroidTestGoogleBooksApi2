package ru.doktorov.testapp.source.service;

import ru.doktorov.testapp.di.RetrofitModule;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module(includes = {RetrofitModule.class})
abstract class GoogleApiModule {
    @Provides
    @Singleton
    static GoogleApi provideGoogleApi(Retrofit retrofit) {
        return retrofit.create(GoogleApi.class);
    }
}