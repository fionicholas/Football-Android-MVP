package com.github.fionicholas.football.ui.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.github.fionicholas.football.ui.match.MatchFragment
import com.github.fionicholas.football.ui.standing.StandingFragment
import com.github.fionicholas.football.ui.team.TeamFragment

class LeaguePagerAdapter(fm: FragmentManager): FragmentPagerAdapter(fm){


    private val pages = listOf(
        MatchFragment(),
        StandingFragment(),
        TeamFragment()

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
            1 -> "Standings"
            else -> "Team"
        }
    }
}