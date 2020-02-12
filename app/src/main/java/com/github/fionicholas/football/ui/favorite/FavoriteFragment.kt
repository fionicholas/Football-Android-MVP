package com.github.fionicholas.football.ui.favorite


import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.github.fionicholas.football.R
import com.github.fionicholas.football.ui.main.MainActivity

/**
 * A simple [Fragment] subclass.
 */
class FavoriteFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_favorite, container, false)
        val viewpager_main = view.findViewById(R.id.viewpager_main) as ViewPager
        val tabs_main = view.findViewById(R.id.tabs_main) as TabLayout


        val cfManager = childFragmentManager
        viewpager_main.setOffscreenPageLimit(0)
        val fragmentAdapter = FavoritePagerAdapter(cfManager)
        viewpager_main.adapter = fragmentAdapter
        tabs_main.setupWithViewPager(viewpager_main)

        return view
    }

    override fun onResume() {
        super.onResume()
        (activity as MainActivity)
            .setActionBarTitle("Favorite")
    }
}



