package ru.doktorov.testapp.main.favoritebook;

import ru.doktorov.testapp.base.BasePresenter;
import ru.doktorov.testapp.base.BaseView;
import ru.doktorov.testapp.source.model.Books;
import ru.doktorov.testapp.source.model.Favorites;

public interface FavoriteBookContract {
    interface View extends BaseView<Presenter> {
        void showResult(Books books);
    }

    interface Presenter extends BasePresenter<View> {
        void load();

        void removeBook(Favorites book);
    }
}
