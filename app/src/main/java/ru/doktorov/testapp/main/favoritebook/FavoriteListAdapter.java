package ru.doktorov.testapp.main.favoritebook;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.util.Linkify;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.jakewharton.rxbinding2.view.RxView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.doktorov.testapp.base.BaseViewHolder;
import ru.doktorov.testapp.base.OnItemClickListener;
import ru.doktorov.testapp.source.model.Books;
import ru.doktorov.testapp.R;
import ru.doktorov.testapp.source.model.Favorites;

public class FavoriteListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final int REPOSITORY_VIEW_TYPE = 0;

    private Context mContext;

    private OnItemClickListener mOnItemClickListener;

    private Books mBooks;

    FavoriteListAdapter(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    public void setBooks(Books books) {
        mBooks = books;

        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        return REPOSITORY_VIEW_TYPE;
    }

    @Override
    public int getItemCount() {
        return mBooks == null ? 0 : mBooks.getBooks().size();
    }

    public Favorites getItem(int position) {
        return mBooks.getBooks().get(position);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        switch (viewType) {
            case REPOSITORY_VIEW_TYPE:
                View viewRepository = LayoutInflater.from(mContext).inflate(R.layout.list_item_search, parent, false);
                return new BookViewHolderHolder(viewRepository);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Favorites favorites = mBooks.getBooks().get(position);
        BookViewHolderHolder userViewHolder = (BookViewHolderHolder) holder;
        userViewHolder.showProcess(favorites, position);
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
            Picasso.with(mView.getContext()).load(book.getThumbnail()).resize(128, 280).centerInside().into(mBookThumbnail);

            mBookAddFavorites.setText(mContext.getResources().getString(R.string.remove_from_favorites));
            RxView.clicks(mBookAddFavorites).subscribe(aVoid -> {
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onAddFavorites(book);
                }
            });
        }
    }

    void clear() {
        mBooks.setBooks(new ArrayList<>());

        notifyDataSetChanged();
    }
}
