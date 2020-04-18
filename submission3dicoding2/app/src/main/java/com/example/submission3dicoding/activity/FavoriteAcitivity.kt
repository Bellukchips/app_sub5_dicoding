package com.example.submission3dicoding.activity

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.support.design.widget.BottomNavigationView
import android.view.Menu
import android.view.MenuItem
import com.example.submission3dicoding.R
import com.example.submission3dicoding.fragment.FavoriteMovieFragment
import com.example.submission3dicoding.fragment.FavoriteTvShowFragment
import com.example.submission3dicoding.fragment.MovieFragment
import com.example.submission3dicoding.fragment.TvShowFragment

class FavoriteAcitivity : AppCompatActivity() {
    private val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_favorite_film ->{
                val movie = FavoriteMovieFragment()
                supportFragmentManager.beginTransaction().replace(R.id.frame_container2,movie).commit()
                setTitle(R.string.favorite_film)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_favorite_tv -> {
                val tv = FavoriteTvShowFragment()
                supportFragmentManager.beginTransaction().replace(R.id.frame_container2,tv).commit()
                setTitle(R.string.favorite_tv_show)
                return@OnNavigationItemSelectedListener true
            }

        }
        false
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.more_vert,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId== R.id.ac_settings){
            val i = Intent(Settings.ACTION_LOCALE_SETTINGS)
            startActivity(i)

        }else if(item?.itemId == R.id.reminder){
            val i = Intent(this@FavoriteAcitivity,ReminderActivity::class.java)
            startActivity(i)}
        return super.onOptionsItemSelected(item)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite_acitivity)
        setTitle(R.string.favorite)
        val navView: BottomNavigationView = findViewById(R.id.nav_view2)
        navView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)
        navView.selectedItemId = R.id.navigation_favorite_film
        setTitle(R.string.favorite_film)
        if (navView.selectedItemId == R.id.navigation_favorite_film){
            setTitle(R.string.favorite_film)
        }else if (navView.selectedItemId == R.id.navigation_favorite_tv){
            setTitle(R.string.favorite_tv_show)
        }


    }
}
