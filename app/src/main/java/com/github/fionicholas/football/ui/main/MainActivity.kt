package com.github.fionicholas.football.ui.main

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.github.fionicholas.football.R
import com.github.fionicholas.football.ui.favorite.FavoriteFragment
import com.github.fionicholas.football.ui.league.LeagueFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.title = "Football"

        bottom_navigation.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.leagues -> {
                    loadLeagueFragment(savedInstanceState)
                }
                R.id.favorites_match -> {
                    loadFavoritesFragment(savedInstanceState)
                }
            }
            true
        }
        bottom_navigation.selectedItemId = R.id.leagues
    }

    private fun loadLeagueFragment(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.container, LeagueFragment(), LeagueFragment::class.java.simpleName)
                .commit()
        }
    }

    private fun loadFavoritesFragment(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.container, FavoriteFragment(), FavoriteFragment::class.java.simpleName)
                .commit()
        }
    }


    fun setActionBarTitle(title: String) {
        supportActionBar?.title = title
    }

}
