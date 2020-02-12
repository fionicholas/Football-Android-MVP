package com.github.fionicholas.football.ui.match


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
import com.github.fionicholas.football.ui.adapter.MatchAdapter
import com.github.fionicholas.football.data.model.Match
import com.github.fionicholas.football.ui.detailleague.DetailLeagueActivity
import com.github.fionicholas.football.ui.detailmatch.DetailMatchActivity
import com.github.fionicholas.football.ui.searchmatch.SearchMatchActivity

/**
 * A simple [Fragment] subclass.
 */
class MatchFragment : Fragment(), MatchView {

    private lateinit var presenter: MatchPresenter
    private lateinit var progressBar: ProgressBar
    private lateinit var recyclerview: RecyclerView
    private lateinit var btnSearch: RelativeLayout
    private lateinit var adapterMatch: MatchAdapter
    private var matchList: MutableList<Match> = mutableListOf()
    lateinit var ACTIVITY: DetailLeagueActivity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_match, container, false)

        progressBar = view.findViewById(R.id.loading1)
        recyclerview = view.findViewById(R.id.rv_match)
        btnSearch = view.findViewById(R.id.layout_search)


        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        adapterMatch =
            MatchAdapter(
                matchList
            ) {
                val intent = Intent(context, DetailMatchActivity::class.java)
                intent.putExtra("DETAIL_MATCH", it.idEvent)
                startActivity(intent)
                Log.d("evnetnemddas", it.idEvent)
            }
        recyclerview.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = adapterMatch
        }
        presenter = MatchPresenter(this)
        presenter.getMatch(ACTIVITY.idLeague)

        btnSearch.setOnClickListener {
            val i = Intent(context, SearchMatchActivity::class.java)
            i.putExtra("ID_LEAGUE", ACTIVITY.idLeague)
            startActivity(i)
        }

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        ACTIVITY = context as DetailLeagueActivity
    }

    override fun showMatch(data: List<Match>) {
        matchList.clear()
        matchList.addAll(data)
        adapterMatch.notifyDataSetChanged()
    }

    override fun showLoading() {
       progressBar.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        progressBar.visibility = View.GONE
    }


}
