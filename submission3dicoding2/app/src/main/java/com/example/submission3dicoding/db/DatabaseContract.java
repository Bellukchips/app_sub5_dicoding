package com.example.submission3dicoding.db;

import android.net.Uri;
import android.provider.BaseColumns;

public class DatabaseContract {
    public static final String AUTHORITY = "com.example.submission3dicoding";
    private static final String SCHEME = "content";

    private DatabaseContract(){

}
    public static String TABLE_NAME = "movies";
    public static final class MovieColumn implements BaseColumns{
        public static final String ID_MOVIE="ID_MOVIE";
        public static final String JUDUL="JUDUL";
        public static final String JENIS="JENIS";
        public static final String PHOTO="PHOTO";
        public static final String DESC="DESC";
        public static final String DATE="DATE";
        public static final Uri CONTENT_URI = new Uri.Builder().scheme(SCHEME)
                .authority(AUTHORITY)
                .appendPath(TABLE_NAME)
                .build();
    }
    }

