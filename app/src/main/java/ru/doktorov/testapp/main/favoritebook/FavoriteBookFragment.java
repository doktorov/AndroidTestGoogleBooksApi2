package ru.doktorov.testapp.main.favoritebook;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import ru.doktorov.testapp.base.BaseFragment;
import ru.doktorov.testapp.base.OnItemClickListener;
import ru.doktorov.testapp.R;
import ru.doktorov.testapp.source.model.Books;
import ru.doktorov.testapp.source.model.Favorites;

import javax.inject.Inject;

public class FavoriteBookFragment extends BaseFragment implements FavoriteBookContract.View,
        OnItemClickListener<Favorites> {
    @Inject
    FavoriteBookContract.Presenter mFavoriteBookPresenter;

    @BindView(R.id.favorites_list)
    RecyclerView mFavoritesList;

    @BindView(R.id.progress)
    View mProgressView;

    @BindView(R.id.empty)
    View mEmptyView;

    private FavoriteListAdapter mFavoriteListAdapter;

    @Inject
    public FavoriteBookFragment() {

    }

    public static FavoriteBookFragment newInstance() {
        Bundle args = new Bundle();
        FavoriteBookFragment fragment = new FavoriteBookFragment();
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
        return inflater.inflate(R.layout.fragment_favorites_item, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mFavoritesList.setLayoutManager(mLayoutManager);

        mFavoriteListAdapter = new FavoriteListAdapter(this);
        mFavoritesList.setAdapter(mFavoriteListAdapter);

        mFavoriteBookPresenter.load();
    }

    @Override
    public void onResume() {
        super.onResume();

        mFavoriteBookPresenter.takeView(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        mFavoriteBookPresenter.dropView();
    }

    @Override
    public void showEmpty() {
        mEmptyView.setVisibility(View.VISIBLE);
        mProgressView.setVisibility(View.GONE);
        mFavoritesList.setVisibility(View.GONE);
        mFavoriteListAdapter.clear();
    }

    @Override
    public void showError() {

    }

    @Override
    public void showLoading() {
        mProgressView.setVisibility(View.VISIBLE);
        mFavoritesList.setVisibility(View.GONE);
        mEmptyView.setVisibility(View.GONE);
    }

    @Override
    public void hideLoading() {
        mProgressView.setVisibility(View.GONE);
        mFavoritesList.setVisibility(View.VISIBLE);
        mEmptyView.setVisibility(View.GONE);
    }

    @Override
    public void showResult(Books books) {
        mFavoriteListAdapter.setBooks(books);
    }

    @Override
    public void onAddFavorites(Favorites book) {
        mFavoriteBookPresenter.removeBook(book);
    }
}
