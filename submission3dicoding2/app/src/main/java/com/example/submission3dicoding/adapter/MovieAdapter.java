package com.example.submission3dicoding.adapter;


import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import com.example.submission3dicoding.R;
import com.example.submission3dicoding.activity.DetailActivity;
import com.example.submission3dicoding.model.ModelMovie;
import java.util.ArrayList;
public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.CategoryViewHolder> {

    private Context context;
    private Boolean pindah_fragment;
    private ArrayList<ModelMovie>modelMovies;
    private static final String file_path = "https://image.tmdb.org/t/p/w342";
    public MovieAdapter(Context context,Boolean pindah_fragment) {
        this.context = context;
        this.pindah_fragment = pindah_fragment;
        modelMovies = new ArrayList<>();
    }
    public ArrayList<ModelMovie> getListMovies() {
        return modelMovies;
    }



    public void setModelMovies(ArrayList<ModelMovie> m) {
        modelMovies.clear();
        modelMovies.addAll(m);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_rcyle,viewGroup,false);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final CategoryViewHolder categoryViewHolder,final int i) {
        categoryViewHolder.name.setText(modelMovies.get(i).getName());
        categoryViewHolder.deskripsi.setText(modelMovies.get(i).getDeskripsi());
        categoryViewHolder.date.setText(modelMovies.get(i).getTnggal());
        Glide.with(categoryViewHolder.itemView.getContext()).load(file_path+modelMovies.get(i).getPhoto()).into(categoryViewHolder.img);
        categoryViewHolder.detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent getfile = new Intent(categoryViewHolder.itemView.getContext(), DetailActivity.class);
                getfile.putExtra("data",modelMovies.get(i).getId_movie());

                if (pindah_fragment == true) {
                    getfile.putExtra("jenis",true);
                } else{
                    getfile.putExtra("jenis",false);
                }
                v.getContext().startActivity(getfile);

            }
        });

    }

    @Override
    public int getItemCount() {
        return modelMovies.size();
    }

    public class CategoryViewHolder extends RecyclerView.ViewHolder {

        TextView name;
        TextView deskripsi;
        ImageView img;
        TextView date;
        Button detail;
        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.nama_movie);
            deskripsi = itemView.findViewById(R.id.deskripsi);
            img = itemView.findViewById(R.id.img_view);
            detail = itemView.findViewById(R.id.detail);
            date = itemView.findViewById(R.id.tgl);

        }

    }
}
