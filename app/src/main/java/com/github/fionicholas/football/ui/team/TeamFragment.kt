package com.github.fionicholas.football.ui.team


import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.RelativeLayout

import com.github.fionicholas.football.R
import com.github.fionicholas.football.ui.adapter.TeamAdapter
import com.github.fionicholas.football.data.model.Team
import com.github.fionicholas.football.ui.detailleague.DetailLeagueActivity
import com.github.fionicholas.football.ui.detailteam.DetailTeamActivity
import com.github.fionicholas.football.ui.searchteam.SearchTeamActivity

/**
 * A simple [Fragment] subclass.
 */
class TeamFragment : Fragment(), Teamview {

    private lateinit var presenter: TeamPresenter
    private lateinit var progressBar: ProgressBar
    private lateinit var recyclerview: RecyclerView
    private lateinit var btnSearch: RelativeLayout
    private lateinit var adapterTeam: TeamAdapter
    private var teamList: MutableList<Team> = mutableListOf()
    lateinit var ACTIVITY: DetailLeagueActivity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_team, container, false)

        progressBar = view.findViewById(R.id.loading)
        recyclerview = view.findViewById(R.id.recycler_team)
        btnSearch = view.findViewById(R.id.layout_search)

        btnSearch.setOnClickListener {
            val i = Intent(context, SearchTeamActivity::class.java)
            i.putExtra("ID_LEAGUE", ACTIVITY.idLeague)
            startActivity(i)
        }
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        adapterTeam =
            TeamAdapter(
                teamList
            ) {
                val intent = Intent(context, DetailTeamActivity::class.java)
                intent.putExtra("DETAIL_TEAM", it.idTeam)
                intent.putExtra("NAME_TEAM", it.strTeam)
                startActivity(intent)
                Log.d("evnetnemddas", it.idTeam)
            }
        recyclerview.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = adapterTeam
        }
        presenter = TeamPresenter(this)
        presenter.getTeams(ACTIVITY.idLeague)

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        ACTIVITY = context as DetailLeagueActivity
    }

    override fun showTeam(data: List<Team>) {
        teamList.clear()
        teamList.addAll(data)
        adapterTeam.notifyDataSetChanged()
    }

    override fun showLoading() {
        progressBar.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        progressBar.visibility = View.GONE
    }


}