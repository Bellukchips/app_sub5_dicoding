package com.example.submission3dicoding.fragment;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ProgressBar;
import android.widget.Toast;
import com.example.submission3dicoding.R;
import com.example.submission3dicoding.adapter.MovieAdapter;
import com.example.submission3dicoding.db.MovieHelper;
import com.example.submission3dicoding.model.ModelMovie;
import com.example.submission3dicoding.viewmodel.MainViewModel;

import java.util.ArrayList;

import static com.example.submission3dicoding.db.DatabaseContract.MovieColumn.CONTENT_URI;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavoriteTvShowFragment extends Fragment {
    private MovieAdapter movieAdapter;
    private MovieHelper movieHelper;
    private static final String EXTRA_STATE= "EXTRA_STATE";
    private ProgressBar progressBar;
    private HandlerThread handlerThread;
    private RecyclerView recyclerView;
    private Boolean hasData = false;
    private FavoriteMovieFragment.DataObserver myObserver;
    private MainViewModel mainViewModel;


    @Override
    public void onStart() {
        super.onStart();
        MovieHelper movieHelper = MovieHelper.getInstance(getActivity());
        movieHelper.OpenDb();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        MovieHelper movieHelper = MovieHelper.getInstance(getActivity());

        movieHelper.CloseDb();
    }
    public FavoriteTvShowFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.fragment_favorite_tv_show,container,false);
        progressBar = view.findViewById(R.id.progress_tv);
        recyclerView = view.findViewById(R.id.tv_fragment);
        mainViewModel = ViewModelProviders.of(getActivity()).get(MainViewModel.class);
        mainViewModel.getFavoritedatatv().observe(this,getMovies);

        handlerThread = new HandlerThread("DataObserver");
        handlerThread.start();
        Handler handler = new Handler(handlerThread.getLooper());
        myObserver = new FavoriteMovieFragment.DataObserver(handler,view.getContext());
        getContext().getContentResolver().registerContentObserver(CONTENT_URI,true,myObserver);


        movieAdapter = new MovieAdapter(getActivity(),true);
        recyclerView.setAdapter(movieAdapter);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        movieAdapter.notifyDataSetChanged();
        setData();

        return view;
    }

    private Observer<ArrayList<ModelMovie>> getMovies = new Observer<ArrayList<ModelMovie>>() {
        @Override
        public void onChanged(ArrayList<ModelMovie> getMovies) {
            if (getMovies != null) {
                movieAdapter.setModelMovies(getMovies);
                showLoading(false);
                hasData = true;
            }else{
                showLoading(true);
                hasData= false;
                Log.d("Favorite TV Fragment","Missing Data");

            }
        }
    };
    private void setData() {
        try {
            mainViewModel.setFavoritTv(getContext());
            showLoading(true);

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public  void showLoading(Boolean loading){
        if (loading == true){
            progressBar.setVisibility(View.VISIBLE);
        }else {
            progressBar.setVisibility(View.GONE);
        }
    }
    public static class DataObserver extends ContentObserver {
        private MainViewModel mainViewModel;
        final Context context;
        private FavoriteTvShowFragment favoriteTvShowFragment;

        @Override
        public void onChange(boolean selfChange, Uri uri) {
            super.onChange(selfChange, uri);
            mainViewModel = ViewModelProviders.of(favoriteTvShowFragment.getActivity()).get(MainViewModel.class);
            mainViewModel.getFavoritedatatv().observe(favoriteTvShowFragment,favoriteTvShowFragment.getMovies);


        }

        public DataObserver(Handler handler, Context context) {
            super(handler);
            this.context = context;
        }

    }

}
