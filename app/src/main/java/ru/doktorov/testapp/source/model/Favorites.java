package ru.doktorov.testapp.source.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

@Entity(tableName = "favorites")
public final class Favorites {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "entryid")
    private final int mId;

    @NonNull
    @ColumnInfo(name = "googleid")
    private final String mGoogleId;

    @Nullable
    @ColumnInfo(name = "thumbnail")
    private final String mThumbnail;

    @Nullable
    @ColumnInfo(name = "title")
    private final String mTitle;

    @Nullable
    @ColumnInfo(name = "authors")
    private final String mAuthors;

    @Nullable
    @ColumnInfo(name = "previewlink")
    private final String mPreviewLink;

    public Favorites(int id, @NonNull String googleId, @Nullable String thumbnail, @Nullable String title,
                     @Nullable String authors, @Nullable String previewLink) {
        mId = id;
        mGoogleId = googleId;

        mThumbnail = thumbnail;
        mTitle = title;
        mAuthors = authors;
        mPreviewLink = previewLink;
    }

    public int getId() {
        return mId;
    }

    @NonNull
    public String getGoogleId() {
        return mGoogleId;
    }

    @Nullable
    public String getThumbnail() {
        return mThumbnail;
    }

    @Nullable
    public String getTitle() {
        return mTitle;
    }

    @Nullable
    public String getAuthors() {
        return mAuthors;
    }

    @Nullable
    public String getPreviewLink() {
        return mPreviewLink;
    }
}
