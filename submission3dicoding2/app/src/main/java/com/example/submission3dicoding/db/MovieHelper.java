package com.example.submission3dicoding.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import com.example.submission3dicoding.model.ModelMovie;

import java.util.ArrayList;


import static com.example.submission3dicoding.db.DatabaseContract.MovieColumn.*;
import static com.example.submission3dicoding.db.DatabaseContract.TABLE_NAME;

public class MovieHelper {
    public static final String DATABASE_NAME = TABLE_NAME;
    public static DatabaseHelper databaseHelper;
    public static MovieHelper INSTANCE;
    private static SQLiteDatabase database;

    private MovieHelper(Context context) {
        databaseHelper = new DatabaseHelper(context);
    }

    public static MovieHelper getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (SQLiteOpenHelper.class) {
                if (INSTANCE == null) {
                    INSTANCE = new MovieHelper(context);

                }
            }

        }
        return INSTANCE;
    }
    public void OpenDb() throws SQLException {
        database = databaseHelper.getWritableDatabase();

    }

    public void CloseDb() {
        databaseHelper.close();
        if (database.isOpen()) {
            database.close();
        }
    }

    public ArrayList<ModelMovie> getFilm(String jenis) {
        ArrayList<ModelMovie> modelMovies = new ArrayList<>();



            Cursor cursor = database.rawQuery("select * from "+TABLE_NAME+" where "+JENIS+" = "+jenis, null);
            cursor.moveToFirst();
            ModelMovie data;
            if (cursor.getCount() > 0) {
                do {
                    data = new ModelMovie();
                    data.setId_movie(cursor.getInt(cursor.getColumnIndexOrThrow(ID_MOVIE)));
                    data.setJenis(cursor.getString(cursor.getColumnIndexOrThrow(JENIS)));
                    data.setName(cursor.getString(cursor.getColumnIndexOrThrow(JUDUL)));
                    data.setPhoto(cursor.getString(cursor.getColumnIndexOrThrow(PHOTO)));
                    data.setDeskripsi(cursor.getString(cursor.getColumnIndexOrThrow(DESC)));
                    data.setTnggal(cursor.getString(cursor.getColumnIndexOrThrow(DATE)));

                    modelMovies.add(data);
                    cursor.moveToNext();
                }while (!cursor.isAfterLast());
            }else{
                Log.d("NULL 1","1");
                modelMovies = null;
            }
            cursor.close();



        return modelMovies;

    }
    public long insertProvider(ContentValues values) {
        return database.insert(DATABASE_NAME, null, values);
    }

    public int deleteProvider(String id) {
        return database.delete(DATABASE_NAME, ID_MOVIE + " = "+id,null);
    }
    public Cursor queryProviderMovie() {
        return database.rawQuery("SELECT * FROM "+TABLE_NAME+" WHERE "+JENIS+" = 1", null);
    }

    public Cursor queryProviderTv() {
        return database.rawQuery("SELECT * FROM "+TABLE_NAME+" WHERE "+JENIS+" = 2", null);
    }
    public Cursor queryByIdProvider(String id) {
        return database.query(DATABASE_NAME, null
                , ID_MOVIE + " = ?"
                , new String[]{id}
                , null
                , null
                , null
                , null);
    }
    public long insertData(ModelMovie modelMovie){
        ContentValues values = new ContentValues();
        values.put(JUDUL,modelMovie.getName());
        values.put(JENIS,modelMovie.getJenis());
        values.put(PHOTO,modelMovie.getPhoto());
        values.put(DESC,modelMovie.getDeskripsi());
        values.put(DATE,modelMovie.getTnggal());
        values.put(ID_MOVIE,modelMovie.getId_movie());
        return database.insert(DATABASE_NAME,null,values);
    }

    public int deleteData(int id){
        return database.delete(TABLE_NAME,ID_MOVIE +" = '"+ id+"'",null);
    }
    public ModelMovie searchData(int id){
        Cursor cursor =  database.rawQuery("select * from "+TABLE_NAME+" where "+ID_MOVIE+" = "+id,null);
        ModelMovie modelMovie = null;
        cursor.moveToFirst();
        if (cursor.getCount()>0){

                modelMovie = new ModelMovie();
                modelMovie.setId_movie(cursor.getInt(cursor.getColumnIndexOrThrow(ID_MOVIE)));
                cursor.close();
        } else {
            modelMovie = null;
        }
        return  modelMovie;
    }


}
