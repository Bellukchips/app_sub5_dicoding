package com.example.submission3dicoding.viewmodel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.util.Log;
import com.example.submission3dicoding.db.MovieHelper;
import com.example.submission3dicoding.model.ModelMovie;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class MainViewModel extends ViewModel {
    private static final  String API_KEY = "ed94c15844d5687a41edbd52b892330d";
    private MutableLiveData<ArrayList<ModelMovie>> listMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<ArrayList<ModelMovie>> listMutablefavoritedata = new MutableLiveData<>();
    private MutableLiveData<ArrayList<ModelMovie>> listMutablefavoritedatatv = new MutableLiveData<>();
    private MutableLiveData<ModelMovie> mutableLiveData = new MutableLiveData<>();
    private static String url = new String();
    public void setMovie(final Boolean type,int id_movie){
        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
        final ModelMovie mMovie = new ModelMovie();
        if (type == true) {
            url="https://api.themoviedb.org/3/movie/"+id_movie+"?api_key="+API_KEY+"&language=en-US";
        } else {
            url="https://api.themoviedb.org/3/tv/"+id_movie+"?api_key="+API_KEY+"&language=en-US";
        }

        asyncHttpClient.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String res = new String(responseBody);
                    JSONObject responeObj = new JSONObject(res);
                    ModelMovie modelMovie;
                    if (type==true){
                        modelMovie = new ModelMovie(responeObj,true);

                    } else {
                        modelMovie = new ModelMovie(responeObj, false);
                    }
                    mMovie.setName(modelMovie.getName());
                    mMovie.setDeskripsi(modelMovie.getDeskripsi());
                    mMovie.setTnggal(modelMovie.getTnggal());
                    mMovie.setId_movie(modelMovie.getId_movie());
                    // mMovie.setVote(modelMovie.getVote());
                    mMovie.setPhoto(modelMovie.getPhoto());

                    mutableLiveData.postValue(mMovie);

                } catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.d("onFailure", error.getMessage());
            }
        });
    }

    public void searchMovies(final Boolean type,String s){
        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
        final ArrayList<ModelMovie> sMovies = new ArrayList<>();
        if (type) {
            url="https://api.themoviedb.org/3/search/movie?api_key="+API_KEY+"&language=en-US&query="+s;
        } else {
            url="https://api.themoviedb.org/3/search/tv?api_key="+API_KEY+"&language=en-US&query="+s;
        }

        asyncHttpClient.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String res = new String(responseBody);
                    JSONObject responeObj = new JSONObject(res);
                    JSONArray list = responeObj.getJSONArray("results");
                    for (int i=0;i<list.length();i++){
                        JSONObject mObject = list.getJSONObject(i);
                        ModelMovie modelMovie;
                        if (type){
                            modelMovie = new ModelMovie(mObject,true);

                        } else {
                            modelMovie = new ModelMovie(mObject, false);
                        }
                        sMovies.add(modelMovie);

                    }
                    listMutableLiveData.postValue(sMovies);

                } catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.d("onFailure", error.getMessage());
            }
        });
    }


    public void setMovies(final Boolean pindah_fragment){
        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
        final ArrayList<ModelMovie> movieArrayList = new ArrayList<>();
        if (pindah_fragment == true) {
            url = "https://api.themoviedb.org/3/discover/movie?api_key="+API_KEY+"&language=en-US";

        } else if (pindah_fragment == false){
            url = "https://api.themoviedb.org/3/discover/tv?api_key="+API_KEY+"&language=en-US";

        }

        asyncHttpClient.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String res = new String(responseBody);
                    JSONObject responeObj = new JSONObject(res);
                    JSONArray list = responeObj.getJSONArray("results");
                    for (int i=0;i<list.length();i++){
                        JSONObject mObject = list.getJSONObject(i);
                        ModelMovie modelMovie;
                        if (pindah_fragment==true){
                            modelMovie = new ModelMovie(mObject,true);

                        } else {
                            modelMovie = new ModelMovie(mObject, false);
                        }
                        movieArrayList.add(modelMovie);

                    }
                    listMutableLiveData.postValue(movieArrayList);

                } catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.d("onFailure", error.getMessage());
            }
        });
    }
    public void setFavoriteMovie(Context context){
        MovieHelper movieHelper = MovieHelper.getInstance(context);
        movieHelper.OpenDb();
        ArrayList <ModelMovie>modelMovie = new ArrayList<>();
        modelMovie = movieHelper.getFilm("1");
        if (modelMovie==null){
            Log.d("CHECK","Data Kosong");
        }
         listMutablefavoritedata.postValue(modelMovie);
        movieHelper.CloseDb();

    }
    public LiveData<ArrayList<ModelMovie>> getFavoritedata(){
        return listMutablefavoritedata;
    } public LiveData<ArrayList<ModelMovie>> getFavoritedatatv(){
        return listMutablefavoritedatatv;
    }
    public void setFavoritTv(Context context){
        MovieHelper  movieHelper = MovieHelper.getInstance(context);
        movieHelper.OpenDb();
        ArrayList<ModelMovie>modelMovies = new ArrayList<>();
        modelMovies = movieHelper.getFilm("2");
        if (modelMovies == null){
            Log.d("Check","Data Kosong");

        }
        listMutablefavoritedatatv.postValue(modelMovies);
        movieHelper.CloseDb();
    }


    public LiveData<ArrayList<ModelMovie>> getMovies(){
        return listMutableLiveData;
    }


    public LiveData<ModelMovie> getMovie(){
        return mutableLiveData;
    }

    public LiveData<ArrayList<ModelMovie>> searchFilm() {
        return listMutableLiveData;
    }
}

