package com.github.fionicholas.football.ui.league


import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar

import com.github.fionicholas.football.R
import com.github.fionicholas.football.ui.adapter.LeagueAdapter
import com.github.fionicholas.football.data.model.League
import com.github.fionicholas.football.ui.detailleague.DetailLeagueActivity
import com.github.fionicholas.football.ui.main.MainActivity

/**
 * A simple [Fragment] subclass.
 */
class LeagueFragment : Fragment() {

    private lateinit var rv_football: RecyclerView
    private lateinit var adapterLeague: LeagueAdapter
    private lateinit var progressBar: ProgressBar
    private var football: MutableList<League> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_league, container, false)

        rv_football = view.findViewById(R.id.rv_league)
        progressBar = view.findViewById(R.id.progress_league)

        adapterLeague = LeagueAdapter(football) {
            val intent = Intent(view.context, DetailLeagueActivity::class.java)
            intent.putExtra("DETAIL_LEAGUE", it)
            startActivity(intent)
        }
        rv_football.apply {
            layoutManager = GridLayoutManager(view.context, 2)
            adapter = adapterLeague
        }
        initData()
    return view
    }
    private fun initData(){

        val name = resources.getStringArray(R.array.league_name)
        val id = resources.getStringArray(R.array.league_id)
        val image = resources.obtainTypedArray(R.array.league_image)
        val description = resources.getStringArray(R.array.league_description)
        football.clear()

        for (i in name.indices) {
            football.add(
                League(name[i], id[i], description[i],
                    image.getResourceId(i, 0))
            )

        }

        image.recycle()
        progressBar.visibility = View.GONE
    }

    override fun onResume() {
        super.onResume()
        (activity as MainActivity)
            .setActionBarTitle("Football")
    }
}


