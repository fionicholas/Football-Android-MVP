package com.github.fionicholas.football.ui.league


import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.fionicholas.football.R
import com.github.fionicholas.football.data.model.League
import com.github.fionicholas.football.ui.adapter.LeagueAdapter
import com.github.fionicholas.football.ui.detailleague.DetailLeagueActivity
import com.github.fionicholas.football.ui.main.MainActivity
import kotlinx.android.synthetic.main.fragment_league.*

class LeagueFragment : Fragment() {

    private lateinit var adapterLeague: LeagueAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_league, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        adapterLeague = LeagueAdapter(getLeagueData()) {
            val intent = Intent(requireContext(), DetailLeagueActivity::class.java)
            intent.putExtra("DETAIL_LEAGUE", it)
            startActivity(intent)
        }

        rvLeague.apply {
            layoutManager = GridLayoutManager(requireContext(), 2)
            adapter = adapterLeague
        }
    }

    private fun getLeagueData(): MutableList<League> {
        val investments = mutableListOf<League>()

        val name = resources.getStringArray(R.array.league_name)
        val id = resources.getStringArray(R.array.league_id)
        val image = resources.obtainTypedArray(R.array.league_image)
        val description = resources.getStringArray(R.array.league_description)

        name.forEachIndexed { index, _ ->
            investments.add(
                League(
                    id[index],
                    name[index],
                    description[index],
                    image.getResourceId(index, 0)
                )
            )
        }

        image.recycle()
        pbLeague.visibility = View.GONE

        return investments
    }

    override fun onResume() {
        super.onResume()
        (activity as MainActivity)
            .setActionBarTitle("Football")
    }
}


