package ru.doktorov.testapp.main.searchbook;

import ru.doktorov.testapp.base.BasePresenter;
import ru.doktorov.testapp.base.BaseView;
import ru.doktorov.testapp.source.model.Books;
import ru.doktorov.testapp.source.model.Favorites;

public interface SearchBookContract {
    interface View extends BaseView<Presenter> {
        void showResult(Books books);

        void showNextResult(Books books);
    }

    interface Presenter extends BasePresenter<View> {
        void searchBooks(String query, int next);

        void addNewTask(Favorites book);
    }
}
