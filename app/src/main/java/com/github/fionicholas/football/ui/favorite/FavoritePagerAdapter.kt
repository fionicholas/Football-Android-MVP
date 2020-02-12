package com.github.fionicholas.football.ui.favorite

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.github.fionicholas.football.ui.favorite.match.MatchFavoriteFragment
import com.github.fionicholas.football.ui.favorite.team.TeamFavoriteFragment

class FavoritePagerAdapter(fm: FragmentManager): FragmentPagerAdapter(fm){


    private val pages = listOf(
        MatchFavoriteFragment(),
        TeamFavoriteFragment()

    )

    override fun getItem(position: Int): Fragment {
        return pages[position]
    }

    override fun getCount(): Int {
        return pages.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when(position){
            0 -> "Match"
            else -> "Team"
        }
    }
}