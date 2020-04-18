package com.example.submission3dicoding.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.example.submission3dicoding.db.MovieHelper;
import com.example.submission3dicoding.fragment.FavoriteMovieFragment;
import com.example.submission3dicoding.fragment.FavoriteTvShowFragment;

import static com.example.submission3dicoding.db.DatabaseContract.*;
import static com.example.submission3dicoding.db.DatabaseContract.MovieColumn.*;

public class MovieProvider extends ContentProvider {
    private static final int MOVIE = 1;
    private static final int MOVIE_ID = 3;
    private static final int TV = 2;
    private static final int JENIS_MOVIE = 5;
    private static final int JENIS_TV = 6;
    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    private MovieHelper movieHelper;

    static {
        sUriMatcher.addURI(AUTHORITY, TABLE_NAME + "/addmovie", MOVIE);
        sUriMatcher.addURI(AUTHORITY, TABLE_NAME + "/addtv", TV);

        // content://com.dicoding.picodiploma.mynotesapp/note/id
        sUriMatcher.addURI(AUTHORITY, TABLE_NAME + "/#", MOVIE_ID);


        sUriMatcher.addURI(AUTHORITY, TABLE_NAME + "/jenis/m", JENIS_MOVIE);

        sUriMatcher.addURI(AUTHORITY, TABLE_NAME + "/jenis/t", JENIS_TV);
    }

    @Override
    public boolean onCreate() {
        movieHelper = MovieHelper.getInstance(getContext());

        return true;
    }


    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {

        movieHelper.OpenDb();
        Cursor cursor;
        switch (sUriMatcher.match(uri)) {
            case MOVIE_ID:
                cursor = movieHelper.queryByIdProvider(uri.getLastPathSegment());
                break;
            case JENIS_MOVIE:
                cursor = movieHelper.queryProviderMovie();
                break;
            case JENIS_TV:
                cursor = movieHelper.queryProviderTv();
                break;
            default:
                cursor = null;
                break;
        }
        return cursor;
    }

    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        movieHelper.OpenDb();
        long added;
        switch (sUriMatcher.match(uri)) {
            case MOVIE:
                added = movieHelper.insertProvider(values);
                getContext().getContentResolver().notifyChange(CONTENT_URI, new FavoriteMovieFragment.DataObserver(new Handler(), getContext()));
                break;
            case TV:
                added = movieHelper.insertProvider(values);
                getContext().getContentResolver().notifyChange(CONTENT_URI, new FavoriteTvShowFragment.DataObserver(new Handler(), getContext()));
                break;
            default:
                added = 0;
                break;
        }
        return Uri.parse(CONTENT_URI + "/" + added);
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        movieHelper.OpenDb();
        int del;
        switch (sUriMatcher.match(uri)) {
            case MOVIE_ID:
                del = movieHelper.deleteProvider(uri.getLastPathSegment());
                getContext().getContentResolver().notifyChange(CONTENT_URI, new FavoriteMovieFragment.DataObserver(new Handler(), getContext()));
                break;
            default:
                del = 0;
                break;

        }
        return del;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }
}
