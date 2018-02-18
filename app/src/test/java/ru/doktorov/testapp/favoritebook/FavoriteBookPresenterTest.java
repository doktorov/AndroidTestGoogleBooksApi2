package ru.doktorov.testapp.favoritebook;

import android.support.annotation.Nullable;

import com.google.common.collect.Lists;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import ru.doktorov.testapp.main.favoritebook.FavoriteBookContract;
import ru.doktorov.testapp.main.favoritebook.FavoriteBookPresenter;
import ru.doktorov.testapp.source.FavoritesRepository;
import ru.doktorov.testapp.source.model.Favorites;

import static org.mockito.Mockito.when;

public class FavoriteBookPresenterTest {
    private static List<Favorites> FAVORITES;

    @Mock
    private FavoritesRepository mFavoritesRepository;

    @Mock
    private FavoriteBookContract.View mFavoriteBookView;

    private FavoriteBookPresenter mFavoriteBookPresenter;

    @Before
    public void setupFavoritesPresenter() {
        MockitoAnnotations.initMocks(this);

        mFavoriteBookPresenter = new FavoriteBookPresenter();
        mFavoriteBookPresenter.takeView(mFavoriteBookView);

        FAVORITES = Lists.newArrayList(
                new Favorites(1, "googleId1", "thumbnail1", "title1", "authors1", "previewLink1"),
                new Favorites(2, "googleId2", "thumbnail2", "title2", "authors2", "previewLink2"),
                new Favorites(3, "googleId3", "thumbnail3", "title3", "authors3", "previewLink3"));
    }

    @Test
    public void loadAllFavoritesFromRepositoryAndLoadIntoView() {
        mFavoriteBookPresenter.load();
    }
}
