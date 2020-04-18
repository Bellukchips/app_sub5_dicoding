package com.example.submission3dicoding.mapping;

import android.database.Cursor;
import android.view.Display;
import com.example.submission3dicoding.model.ModelMovie;

import java.util.ArrayList;

import static com.example.submission3dicoding.db.DatabaseContract.MovieColumn.*;

public class MappingHelper {
        public static ArrayList<ModelMovie> mapCursor(Cursor cursor) {
            ArrayList<ModelMovie> modelMovies = new ArrayList<>();
            while (cursor.moveToNext()) {
            ModelMovie modelMovie = new ModelMovie();
            modelMovie.setId_movie(cursor.getInt(cursor.getColumnIndexOrThrow(ID_MOVIE)));
            modelMovie.setName(cursor.getString(cursor.getColumnIndexOrThrow(JUDUL)));
            modelMovie.setJenis(cursor.getString(cursor.getColumnIndexOrThrow(JENIS)));
            modelMovie.setDeskripsi(cursor.getString(cursor.getColumnIndexOrThrow(DESC)));
            modelMovie.setTnggal(cursor.getString(cursor.getColumnIndexOrThrow(DATE)));
            modelMovie.setPhoto(cursor.getString(cursor.getColumnIndexOrThrow(PHOTO)));
            modelMovies.add(modelMovie);
            }
            return modelMovies;
        }
    public static ModelMovie mapModel(Cursor model){
        ModelMovie modelMovie = new ModelMovie();
        model.moveToNext();

        if (model.getCount() >0){
            modelMovie.setId_movie(model.getInt(model.getColumnIndexOrThrow(ID_MOVIE)));
            modelMovie.setName(model.getString(model.getColumnIndexOrThrow(JUDUL)));
            modelMovie.setJenis(model.getString(model.getColumnIndexOrThrow(JENIS)));
            modelMovie.setPhoto(model.getString(model.getColumnIndexOrThrow(PHOTO)));
            modelMovie.setTnggal(model.getString(model.getColumnIndexOrThrow(DATE)));
            modelMovie.setDeskripsi(model.getString(model.getColumnIndexOrThrow(DESC)));
            model.close();
        }else {
            modelMovie = null;

        }
        return modelMovie;
    }
}
