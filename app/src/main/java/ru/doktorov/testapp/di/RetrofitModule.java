package ru.doktorov.testapp.di;

import android.util.Log;

import ru.doktorov.testapp.source.service.GoogleApi;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public abstract class RetrofitModule {
    private static final String GOOGLE_API_URL = "GOOGLE_API_URL";
    private static final String URL = "https://www.googleapis.com/books/v1/";

    @Provides
    @Named(GOOGLE_API_URL)
    static String provideBaseUrlString() {
        return URL;
    }

    @Provides
    @Singleton
    static Converter.Factory provideGsonConverter() {
        return GsonConverterFactory.create();
    }

    @Provides
    @Singleton
    static Retrofit provideRetrofit(Converter.Factory converter, @Named(GOOGLE_API_URL) String baseUrl) {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder().addInterceptor(chain -> {
            Request original = chain.request();
            Request request = original.newBuilder()
                    .method(original.method(), original.body())
                    .build();

            return chain.proceed(request);
        });

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor(message ->
                Log.d("Test Application", message)).setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient client = httpClient.addInterceptor(interceptor).build();

        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(converter)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(client)
                .build();
    }

    @Provides
    @Singleton
    static GoogleApi provideGoogleApi(Retrofit retrofit) {
        return retrofit.create(GoogleApi.class);
    }
}

