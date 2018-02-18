package ru.doktorov.testapp.main.searchbook;

import android.text.TextUtils;

import ru.doktorov.testapp.source.service.GsonBooks;
import ru.doktorov.testapp.source.FavoritesRepository;
import ru.doktorov.testapp.source.model.Favorites;
import ru.doktorov.testapp.source.model.Books;
import ru.doktorov.testapp.source.service.GoogleApi;
import ru.doktorov.testapp.di.FragmentScoped;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;
import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

@FragmentScoped
public final class SearchBookPresenter implements SearchBookContract.Presenter {

    @Inject
    GoogleApi mGoogleApi;

    @Inject
    FavoritesRepository mTasksRepository;

    @Nullable
    private SearchBookContract.View mView;

    private CompositeDisposable mCompositeDisposable;

    @Inject
    SearchBookPresenter() {
        mCompositeDisposable = new CompositeDisposable();
    }

    @Override
    public void searchBooks(String query, int next) {
        if (mView == null) return;

        if (TextUtils.isEmpty(query)) {
            mView.showEmpty();
        }

        if (next == 0) {
            mView.showLoading();
        }

        mCompositeDisposable.add(mGoogleApi.searchBooks(query, next)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(this::onLoadingSuccess)
                .onErrorReturn(throwable -> onLoadingError())
                .subscribe(books -> {
                    mView.hideLoading();

                    if (books.isConnect()) {
                        if (next == 0) {
                            mView.showResult(books);
                        } else {
                            mView.showNextResult(books);
                        }
                    } else {
                        mView.showError();
                    }
                })
        );
    }

    @Override
    public void addNewTask(Favorites book) {
        mTasksRepository.saveFavorite(book);
    }

    @Override
    public void takeView(SearchBookContract.View view) {
        mView = view;
    }

    @Override
    public void dropView() {
        mView = null;
        mCompositeDisposable.dispose();
    }

    private Books onLoadingSuccess(GsonBooks gsonBooks) {
        List<Favorites> books = new ArrayList<>();

        for (GsonBooks.Item item : gsonBooks.items) {
            String thumbnail = "";
            if (item.volumeInfo.imageLinks != null && item.volumeInfo.imageLinks.thumbnail != null) {
                thumbnail = item.volumeInfo.imageLinks.thumbnail;
            }
            String authors = "";
            if (item.volumeInfo.authors != null) {
                authors = TextUtils.join(", ", item.volumeInfo.authors);
            }

            Favorites book = new Favorites(0, item.id,
                    thumbnail,
                    item.volumeInfo.title,
                    authors,
                    item.volumeInfo.previewLink);

            books.add(book);
        }

        Books booksResult = new Books();
        booksResult.setIsConnect(true);
        booksResult.setTotalItems(gsonBooks.totalItems);
        booksResult.setBooks(books);

        return booksResult;
    }

    private Books onLoadingError() {
        Books books = new Books();
        books.setIsConnect(false);

        return books;
    }
}
