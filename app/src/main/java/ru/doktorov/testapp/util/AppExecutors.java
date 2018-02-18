package ru.doktorov.testapp.util;

import java.util.concurrent.Executor;

import javax.inject.Singleton;

@Singleton
public class AppExecutors {

    private final Executor diskIO;

    public AppExecutors(Executor diskIO) {
        this.diskIO = diskIO;
    }

    public Executor diskIO() {
        return diskIO;
    }
}
