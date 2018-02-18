package ru.doktorov.testapp.searchbook;

import org.junit.Before;
import org.mockito.Mock;

import ru.doktorov.testapp.main.searchbook.SearchBookContract;
import ru.doktorov.testapp.source.FavoritesRepository;

public class SearchBookPresenterTest {
    @Mock
    private FavoritesRepository mFavoritesRepository;

    @Mock
    private SearchBookContract.View mSearchBookView;

    @Before
    public void setupSearchBookPresenter() {

    }
}
