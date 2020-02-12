package com.github.fionicholas.football.ui.standing


import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar

import com.github.fionicholas.football.R
import com.github.fionicholas.football.ui.adapter.StandingAdapter
import com.github.fionicholas.football.data.model.Standing
import com.github.fionicholas.football.ui.detailleague.DetailLeagueActivity

/**
 * A simple [Fragment] subclass.
 */
class StandingFragment : Fragment(), StandingView {

    private lateinit var presenter: StandingPresenter
    private lateinit var progressBar: ProgressBar
    private lateinit var recyclerview: RecyclerView
    private lateinit var adapterMatch: StandingAdapter
    private var standingList: MutableList<Standing> = mutableListOf()
    lateinit var ACTIVITY: DetailLeagueActivity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_standing, container, false)
        progressBar = view.findViewById(R.id.loading)
        recyclerview = view.findViewById(R.id.rv_standing)

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        adapterMatch = StandingAdapter(standingList)
        recyclerview.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = adapterMatch
        }
        presenter = StandingPresenter(this)
        presenter.getStanding(ACTIVITY.idLeague)

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        ACTIVITY = context as DetailLeagueActivity
    }

    override fun showStanding(data: List<Standing>) {
        standingList.clear()
        standingList.addAll(data)
        adapterMatch.notifyDataSetChanged()
    }

    override fun showLoading() {
        progressBar.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        progressBar.visibility = View.GONE
    }


}
