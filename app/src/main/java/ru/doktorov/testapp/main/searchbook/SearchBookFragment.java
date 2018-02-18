package ru.doktorov.testapp.main.searchbook;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import ru.doktorov.testapp.base.BaseFragment;
import ru.doktorov.testapp.R;
import ru.doktorov.testapp.base.OnItemClickListener;
import ru.doktorov.testapp.base.OnLoadMoreListener;
import ru.doktorov.testapp.source.model.Favorites;
import ru.doktorov.testapp.source.model.Books;
import com.jakewharton.rxbinding2.support.v7.widget.RxSearchView;
import com.jakewharton.rxbinding2.view.RxView;

import javax.inject.Inject;

import butterknife.BindView;
import io.reactivex.disposables.Disposable;

public class SearchBookFragment extends BaseFragment implements SearchBookContract.View,
        OnLoadMoreListener, OnItemClickListener<Favorites> {

    @Inject
    SearchBookContract.Presenter mSearchBookPresenter;

    @BindView(R.id.search_view)
    SearchView mSearchView;

    @BindView(R.id.books_list)
    RecyclerView mBooksList;

    @BindView(R.id.progress)
    View mProgressView;

    @BindView(R.id.message)
    View mLostConnection;

    @BindView(R.id.action_retry)
    Button mRetryButton;

    private SearchBookListAdapter mSearchListAdapter;

    private String mQuery;

    @Inject
    public SearchBookFragment() {

    }

    public static SearchBookFragment newInstance() {
        Bundle args = new Bundle();
        SearchBookFragment fragment = new SearchBookFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search_item, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();

        mSearchBookPresenter.takeView(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        mSearchBookPresenter.dropView();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mBooksList.setLayoutManager(mLayoutManager);

        mSearchListAdapter = new SearchBookListAdapter(mBooksList, this, this);
        mBooksList.setAdapter(mSearchListAdapter);

        initSearchView();

        initRetryButton();
    }

    @Override
    public void onLoadMore() {
        mSearchBookPresenter.searchBooks(mQuery, mSearchListAdapter.getItemCount() + 1);
    }

    @Override
    public void onAddFavorites(Favorites book) {
        mSearchBookPresenter.addNewTask(book);
    }

    @Override
    public void showEmpty() {
        mProgressView.setVisibility(View.GONE);
        mBooksList.setVisibility(View.GONE);
        mSearchListAdapter.clear();
    }

    @Override
    public void showError() {
        mSearchView.setVisibility(View.GONE);
        mBooksList.setVisibility(View.GONE);
        mLostConnection.setVisibility(View.VISIBLE);
    }

    @Override
    public void showLoading() {
        mProgressView.setVisibility(View.VISIBLE);
        mBooksList.setVisibility(View.GONE);
    }

    @Override
    public void hideLoading() {
        mProgressView.setVisibility(View.GONE);
        mBooksList.setVisibility(View.VISIBLE);
        mSearchView.setVisibility(View.VISIBLE);
    }

    @Override
    public void showResult(Books books) {
        mSearchListAdapter.setBooks(books);
    }

    @Override
    public void showNextResult(Books books) {
        mSearchListAdapter.setNextBooks(books);
    }

    private void initSearchView() {
        Disposable sProfileEditPersonal =
                RxSearchView.queryTextChanges(mSearchView).subscribe(aVoid -> {
                    mQuery = aVoid.toString();

                    mSearchBookPresenter.searchBooks(mQuery, 0);
                });

        getCompositeDisposable().add(sProfileEditPersonal);
    }

    private void initRetryButton() {
        Disposable sInfoAddWidgetAdd =
                RxView.clicks(mRetryButton).subscribe(aVoid ->
                        mSearchBookPresenter.searchBooks(mQuery, 0));

        getCompositeDisposable().add(sInfoAddWidgetAdd);
    }
}
