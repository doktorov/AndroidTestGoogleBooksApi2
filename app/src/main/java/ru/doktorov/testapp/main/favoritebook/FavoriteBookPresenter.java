package ru.doktorov.testapp.main.favoritebook;

import android.util.Log;

import javax.annotation.Nullable;
import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import ru.doktorov.testapp.source.FavoritesRepository;
import ru.doktorov.testapp.source.model.Books;
import ru.doktorov.testapp.source.model.Favorites;
import ru.doktorov.testapp.di.FragmentScoped;

@FragmentScoped
public final class FavoriteBookPresenter implements FavoriteBookContract.Presenter {

    @Inject
    FavoritesRepository mTasksRepository;

    @Nullable
    private FavoriteBookContract.View mView;

    private CompositeDisposable mCompositeDisposable;

    @Inject
    public FavoriteBookPresenter() {
        mCompositeDisposable = new CompositeDisposable();
    }

    @Override
    public void takeView(FavoriteBookContract.View view) {
        mView = view;
    }

    @Override
    public void dropView() {

    }

    @Override
    public void load() {
        mCompositeDisposable.add(mTasksRepository.getFavorites()
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(favorites -> {
                    if (favorites != null && favorites.size() != 0) {
                        Books books = new Books();
                        books.setBooks(favorites);
                        books.setTotalItems(favorites.size());

                        if (mView != null) {
                            mView.showResult(books);
                        }
                    } else {
                        if (mView != null) {
                            mView.showEmpty();
                        }
                    }
                }, throwable -> Log.e("MainActivity", "exception getting coupons")));
    }

    @Override
    public void removeBook(Favorites book) {
        mTasksRepository.deleteFavorite(book);
    }
}
