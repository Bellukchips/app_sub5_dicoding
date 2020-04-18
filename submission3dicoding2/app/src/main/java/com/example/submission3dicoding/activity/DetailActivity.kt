package com.example.submission3dicoding.activity

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.bumptech.glide.Glide
import com.example.submission3dicoding.R
import com.example.submission3dicoding.db.MovieHelper
import com.example.submission3dicoding.mapping.MappingHelper
import com.example.submission3dicoding.model.ModelMovie
import com.example.submission3dicoding.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.activity_detail.*
import java.lang.Exception

class DetailActivity : AppCompatActivity() {
    private lateinit var name : TextView
    private lateinit var date : TextView
    private lateinit var desc : TextView
    private lateinit var img : ImageView
    private var idMovie : Int  =0

    private lateinit var modelMovie: ModelMovie
    private lateinit var idMovieUri: Uri
    private lateinit var insertMovieUri:Uri
    private lateinit var insertTvUri:Uri
    private var jenis:Boolean = true
    private lateinit var pgrs: ProgressBar
    private lateinit var mainViewModel: MainViewModel
    private lateinit var img_fav:ImageButton
    private val file_path = "https://image.tmdb.org/t/p/w342"

    override fun onStart() {
        val h = MovieHelper.getInstance(applicationContext);
        h.OpenDb()
        super.onStart()
    }

    override fun onDestroy() {
        val h = MovieHelper.getInstance(applicationContext);
        h.CloseDb()
        super.onDestroy()

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        name = findViewById(R.id.judul)
        date = findViewById(R.id.tangggal)
        desc = findViewById(R.id.desc)
        img  = findViewById(R.id.img_d)
        img_fav = findViewById(R.id.img_fav)

        pgrs = findViewById(R.id.progressBar1)

        mainViewModel = this@DetailActivity?.run{
            ViewModelProviders.of(this).get(MainViewModel::class.java)
        }
        mainViewModel.movie.observe(this,getmovie)
        idMovie = intent.getIntExtra("data",0)
        jenis = intent.getBooleanExtra("jenis",true)
        setData()


        modelMovie = ModelMovie();
        img_fav.setOnClickListener(View.OnClickListener {
            val h = MovieHelper.getInstance(applicationContext)
             val hasil  = h.searchData(modelMovie.id_movie)
            val newMovieModel = ModelMovie()

            if(hasil==null){
                if (jenis){
                    newMovieModel.jenis = "1"
                }else{
                    newMovieModel.jenis = "2"
                }
                newMovieModel.id_movie = modelMovie.id_movie
                newMovieModel.deskripsi = modelMovie.deskripsi
                newMovieModel.name = modelMovie.name
                newMovieModel.photo = modelMovie.photo
                newMovieModel.tnggal = modelMovie.tnggal
                h.insertData(newMovieModel)
                img_fav.setColorFilter(Color.RED)
                Toast.makeText(this,"Berhasil Di Tambah"+ newMovieModel.jenis,Toast.LENGTH_LONG).show()
            } else {
                h.deleteData(modelMovie.id_movie)
                img_fav.setColorFilter(Color.GRAY)
                Toast.makeText(this,"Berhasil Di Hapus",Toast.LENGTH_LONG).show()
                finish()



            }

        })
    }

    private val getmovie = Observer<ModelMovie>{ getmovie->
        if (getmovie != null){
            name.text = getmovie.name
            date.text = getmovie.tnggal
            desc.text = getmovie.deskripsi
            Glide.with(this).load(file_path+getmovie.photo).into(img)


            val h = MovieHelper.getInstance(applicationContext);
            val hasil  = h.searchData(getmovie.id_movie);
            modelMovie =getmovie


            if (hasil!=null){
                img_fav.setColorFilter(Color.RED)
            } else{
                img_fav.setColorFilter(Color.GRAY)
            }

            showLoading(false)
        }


    }
    private fun setData() {
        try {
            if (jenis){
            mainViewModel.setMovie(true,idMovie)
                setTitle(R.string.detail_film)


            }else{
            mainViewModel.setMovie(false,idMovie)
                setTitle(R.string.detail_tv)

            }
            showLoading(true)
        }catch (e:Exception){
            e.printStackTrace()
        }


    }
     fun showLoading(loading:Boolean?){
        if (loading == true){
            pgrs.visibility = View.VISIBLE
            name.visibility = View.INVISIBLE
            desc.visibility = View.INVISIBLE
            date.visibility = View.INVISIBLE
            img.visibility = View.INVISIBLE
            img_fav.visibility = View.INVISIBLE

        }else{
            pgrs.visibility = View.GONE
            name.visibility = View.VISIBLE
            desc.visibility = View.VISIBLE
            date.visibility = View.VISIBLE
            img.visibility = View.VISIBLE
            img_fav.visibility = View.VISIBLE


        }
    }
}
