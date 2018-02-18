package ru.doktorov.testapp.base;

public interface BaseView<T> {
    void showEmpty();

    void showError();

    void showLoading();

    void hideLoading();
}
