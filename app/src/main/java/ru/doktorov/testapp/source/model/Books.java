package ru.doktorov.testapp.source.model;

import java.util.List;

public class Books {
    private int mTotalItems;
    private boolean mIsConnect;
    private List<Favorites> mBooks;

    public int getTotalItems() {
        return mTotalItems;
    }

    public void setTotalItems(int totalItems) {
        this.mTotalItems = totalItems;
    }

    public boolean isConnect() {
        return mIsConnect;
    }

    public void setIsConnect(boolean mIsConnect) {
        this.mIsConnect = mIsConnect;
    }

    public List<Favorites> getBooks() {
        return mBooks;
    }

    public void setBooks(List<Favorites> books) {
        this.mBooks = books;
    }

    public void addBooks(List<Favorites> books) {
        this.mBooks.addAll(books);
    }
}
