package ru.doktorov.testapp.di;

import android.app.Application;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;
import ru.doktorov.testapp.TestApplication;
import ru.doktorov.testapp.source.FavoritesRepository;
import ru.doktorov.testapp.source.FavoritesRepositoryModule;


@Singleton
@Component(modules = {FavoritesRepositoryModule.class,
        ApplicationModule.class,
        ActivityBindingModule.class,
        AndroidSupportInjectionModule.class,
        RetrofitModule.class})
public interface AppComponent extends AndroidInjector<TestApplication> {

    FavoritesRepository getFavoritesRepository();

    @Component.Builder
    interface Builder {

        @BindsInstance
        AppComponent.Builder application(Application application);

        AppComponent build();
    }
}
