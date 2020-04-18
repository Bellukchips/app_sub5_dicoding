package com.example.submission3dicoding.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import static com.example.submission3dicoding.db.DatabaseContract.MovieColumn.*;
import static com.example.submission3dicoding.db.DatabaseContract.TABLE_NAME;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "dbmovie";
    private static final int Databas_VERSION = 1;
    private static final String SQL_CREATE_TABLE_MOVIE = String.format("CREATE TABLE "+TABLE_NAME
                    + " ( "+ _ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"
                    +JUDUL+" TEXT NOT NULL,"
                    +DESC+" TEXT NOT NULL,"
                    +PHOTO+" TEXT NOT NULL,"
                    +DATE+" TEXT NOT NULL,"
                    +JENIS+" TEXT NOT NULL,"
                    +ID_MOVIE+" INTEGER NOT NULL)"


    );


    public DatabaseHelper(Context context){
        super(context,DATABASE_NAME,null,Databas_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE_MOVIE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME);
        onCreate(db);
    }

}
