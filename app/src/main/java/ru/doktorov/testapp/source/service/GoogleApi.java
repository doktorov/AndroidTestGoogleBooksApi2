package ru.doktorov.testapp.source.service;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GoogleApi {
    @GET("volumes")
    Single<GsonBooks> searchBooks(@Query("q") String q, @Query("startIndex") int startIndex);
}