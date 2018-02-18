package ru.doktorov.testapp.main.searchbook;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.text.util.Linkify;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import ru.doktorov.testapp.base.OnItemClickListener;
import ru.doktorov.testapp.base.OnLoadMoreListener;
import ru.doktorov.testapp.R;
import ru.doktorov.testapp.base.BaseViewHolder;
import ru.doktorov.testapp.source.model.Favorites;
import ru.doktorov.testapp.source.model.Books;
import com.jakewharton.rxbinding2.view.RxView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchBookListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final int REPOSITORY_VIEW_TYPE = 0;
    private final int PROGRESS_VIEW_TYPE = 1;

    private boolean mIsLoading;
    private int mVisibleThreshold = 5;
    private int mLastVisibleItem;
    private int mTotalItemCount;

    private LinearLayoutManager mLinearLayoutManager;

    private OnItemClickListener mOnItemClickListener;

    private OnLoadMoreListener mOnLoadMoreListener;

    private Books mBooks;

    SearchBookListAdapter(RecyclerView recyclerView, OnLoadMoreListener onLoadMoreListener,
                          OnItemClickListener onItemClickListener) {
        mOnLoadMoreListener = onLoadMoreListener;
        mOnItemClickListener = onItemClickListener;

        mLinearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                mTotalItemCount = mLinearLayoutManager.getItemCount();
                mLastVisibleItem = mLinearLayoutManager.findLastVisibleItemPosition();

                if (!mIsLoading && mTotalItemCount <= (mLastVisibleItem + mVisibleThreshold)
                        && mTotalItemCount < mBooks.getTotalItems()) {
                    if (mOnLoadMoreListener != null) {
                        mOnLoadMoreListener.onLoadMore();
                    }
                    mIsLoading = true;
                }
            }
        });
    }

    void setBooks(Books books) {
        mBooks = books;

        setLoaded();

        notifyDataSetChanged();
    }

    void setNextBooks(Books books) {
        mBooks.addBooks(books.getBooks());

        setLoaded();

        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        if (mBooks.getBooks().size() == mBooks.getTotalItems()) {
            return REPOSITORY_VIEW_TYPE;
        } else {
            return (position == mBooks.getBooks().size() - 1) ? PROGRESS_VIEW_TYPE : REPOSITORY_VIEW_TYPE;
        }
    }

    @Override
    public int getItemCount() {
        return mBooks == null ? 0 : mBooks.getBooks().size();
    }

    private void setLoaded() {
        mIsLoading = false;
    }

    public Favorites getItem(int position) {
        return mBooks.getBooks().get(position);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context parentContext = parent.getContext();
        switch (viewType) {
            case REPOSITORY_VIEW_TYPE:
                View viewRepository = LayoutInflater.from(parentContext).inflate(R.layout.list_item_search, parent, false);
                return new BookViewHolderHolder(viewRepository);
            case PROGRESS_VIEW_TYPE:
                View viewProgress = LayoutInflater.from(parentContext).inflate(R.layout.list_item_loading, parent, false);
                return new LoadingViewHolderHolder(viewProgress);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof BookViewHolderHolder) {
            Favorites book = mBooks.getBooks().get(position);
            BookViewHolderHolder userViewHolder = (BookViewHolderHolder) holder;
            userViewHolder.showProcess(book, position);
        } else if (holder instanceof LoadingViewHolderHolder) {
            LoadingViewHolderHolder loadingViewHolder = (LoadingViewHolderHolder) holder;
            loadingViewHolder.showProcess(null, 0);
        }
    }

    class BookViewHolderHolder extends RecyclerView.ViewHolder implements BaseViewHolder<Favorites> {
        @BindView(R.id.list_item_book_title)
        TextView mBookTitle;

        @BindView(R.id.list_item_book_thumbnail)
        ImageView mBookThumbnail;

        @BindView(R.id.list_item_book_authors)
        TextView mBookAuthors;

        @BindView(R.id.list_item_book_preview_link)
        TextView mBookPreviewLink;

        @BindView(R.id.list_item_book_add_favorites)
        Button mBookAddFavorites;

        private View mView;

        BookViewHolderHolder(View view) {
            super(view);

            ButterKnife.bind(this, view);

            this.mView = view;
        }

        @Override
        public void showProcess(final Favorites book, final int position) {
            mBookTitle.setText(book.getTitle());
            mBookAuthors.setText(book.getAuthors());
            mBookPreviewLink.setText(book.getPreviewLink());
            Linkify.addLinks(mBookPreviewLink, Linkify.WEB_URLS);
            if (!TextUtils.isEmpty(book.getThumbnail())) {
                Picasso.with(mView.getContext()).
                        load(book.getThumbnail()).
                        resize(128, 280).centerInside().into(mBookThumbnail);
            }

            RxView.clicks(mBookAddFavorites).subscribe(aVoid -> {
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onAddFavorites(book);
                }
            });
        }
    }

    static class LoadingViewHolderHolder extends RecyclerView.ViewHolder implements BaseViewHolder<Favorites> {
        @BindView(R.id.progress)
        ProgressBar mProgressBar;

        LoadingViewHolderHolder(View view) {
            super(view);

            ButterKnife.bind(this, view);
        }

        @Override
        public void showProcess(Favorites book, int position) {
            mProgressBar.setIndeterminate(true);
        }
    }

    void clear() {
        mBooks.setBooks(new ArrayList<>());

        setLoaded();

        notifyDataSetChanged();
    }
}


