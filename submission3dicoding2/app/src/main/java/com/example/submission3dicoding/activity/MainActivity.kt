package com.example.submission3dicoding.activity

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.example.submission3dicoding.R
import com.example.submission3dicoding.fragment.*


class MainActivity : AppCompatActivity() {


    private val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_film ->{
                val movie = MovieFragment()
                supportFragmentManager.beginTransaction().replace(R.id.frame_container,movie).commit()
                setTitle(R.string.judul_film)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_tv -> {
                val tv = TvShowFragment()
                supportFragmentManager.beginTransaction().replace(R.id.frame_container,tv).commit()
                setTitle(R.string.tv_show)
                return@OnNavigationItemSelectedListener true
            }R.id.navigation_favorite->{
            val i = Intent(this@MainActivity,FavoriteAcitivity::class.java)
            startActivity(i)

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
            val i = Intent(this@MainActivity,ReminderActivity::class.java)
            startActivity(i)        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)
        navView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)
        navView.selectedItemId = R.id.navigation_film
        setTitle(R.string.judul_film)
        if (navView.selectedItemId == R.id.navigation_film){
            setTitle(R.string.judul_film)
        }else if (navView.selectedItemId == R.id.navigation_tv){
            setTitle(R.string.tv_show)
        }else{setTitle(R.string.favorite)

        }
    }
}
