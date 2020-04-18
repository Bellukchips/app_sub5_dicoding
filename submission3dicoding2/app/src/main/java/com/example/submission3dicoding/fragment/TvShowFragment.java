package com.example.submission3dicoding.fragment;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.example.submission3dicoding.R;
import com.example.submission3dicoding.adapter.MovieAdapter;
import com.example.submission3dicoding.model.ModelMovie;
import com.example.submission3dicoding.viewmodel.MainViewModel;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class TvShowFragment extends Fragment {

    public TvShowFragment() {
        // Required empty public constructor
    }


    private RecyclerView recyclerView;
    private ArrayList<ModelMovie> movies = new ArrayList<>();
    private MovieAdapter adapter;
    private ProgressBar bar;
    private MainViewModel mainViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.fragment_tv_show,container,false);
        recyclerView = view.findViewById(R.id.rv_tv);
        recyclerView.setHasFixedSize(true);
        bar = view.findViewById(R.id.prgrsbar);
        mainViewModel = ViewModelProviders.of(getActivity()).get(MainViewModel.class);
        mainViewModel.getMovies().observe(this,getFilm);
        adapter = new MovieAdapter(getContext(),false);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        setData();
        adapter.notifyDataSetChanged();
        final EditText searchTv = view.findViewById(R.id.search_tv);
        searchTv.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mainViewModel = ViewModelProviders.of(getActivity()).get(MainViewModel.class);
                mainViewModel.searchFilm().observe(getActivity(),getSearchMovie);
                setDataQuery(s.toString());

            }

            @Override
            public void afterTextChanged(Editable s) {


            }
        });
        return view;

    }
    private Observer<ArrayList<ModelMovie>> getSearchMovie = new Observer<ArrayList<ModelMovie>>() {
        @Override
        public void onChanged(ArrayList<ModelMovie> getMovies) {
            if (getMovies != null) {
                adapter.setModelMovies(getMovies);
                showLoading(false);
                recyclerView.setVisibility(View.VISIBLE);
            }
        }
    };
    private void setDataQuery(String search) {

        try {
            showLoading(true);
            mainViewModel.searchMovies(false,search);
            recyclerView.setVisibility(View.INVISIBLE);

        }catch (Exception e){
            e.printStackTrace();
        }
    }
    private Observer<ArrayList<ModelMovie>> getFilm = new Observer<ArrayList<ModelMovie>>() {
        @Override
        public void onChanged(ArrayList<ModelMovie> getMovies) {
            if (getMovies != null) {
                adapter.setModelMovies(getMovies);
                showLoading(false);
            }else{
                showLoading(true);
      

            }
        }
    };
    private void setData() {
        try {
            mainViewModel.setMovies(false);
            showLoading(true);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public  void showLoading(Boolean loading){
        if (loading == true){
            bar.setVisibility(View.VISIBLE);
        }else {
            bar.setVisibility(View.GONE);
        }
    }


}
