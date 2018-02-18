package ru.doktorov.testapp.base;

public interface BasePresenter<T> {
    void takeView(T view);

    void dropView();
}
