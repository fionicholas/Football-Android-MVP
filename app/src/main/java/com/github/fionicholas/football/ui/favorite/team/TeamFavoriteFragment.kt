package com.github.fionicholas.football.ui.favorite.team


import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast

import com.github.fionicholas.football.R
import com.github.fionicholas.football.data.helper.database
import com.github.fionicholas.football.data.model.FavoriteTeam
import com.github.fionicholas.football.ui.detailteam.DetailTeamActivity
import kotlinx.android.synthetic.main.fragment_team_favorite.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.select

/**
 * A simple [Fragment] subclass.
 */
class TeamFavoriteFragment : Fragment() {

    private var favorites: MutableList<FavoriteTeam> = mutableListOf()
    private lateinit var adapter: TeamFavoriteAdapter
    private lateinit var swipeRefresh: SwipeRefreshLayout
    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_team_favorite, container, false)

        recyclerView = rv_fav_team
        progressBar = loading
        swipeRefresh = swipe_layout

        showFavorite()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        recyclerView = rv_fav_team
        progressBar = loading
        swipeRefresh = swipe_layout

        progressBar.visibility = View.VISIBLE

        adapter =
            TeamFavoriteAdapter(
                favorites
            ) {
                val intent = Intent(context, DetailTeamActivity::class.java)
                intent.putExtra("DETAIL_TEAM", it.teamId.toString())
                intent.putExtra("NAME_TEAM", it.nameTeam.toString())
                startActivity(intent)
            }

        recyclerView.adapter = adapter
        swipeRefresh.setOnRefreshListener {
            swipeRefresh.isRefreshing = false
            showFavorite()
        }

        val handler = Runnable {
            progressBar.visibility = View.INVISIBLE
        }
        Handler().postDelayed(handler,1000)

    }

    private fun showFavorite(){

        favorites.clear()
        context?.database?.use {
            swipeRefresh.isRefreshing = false
            val result = select(FavoriteTeam.TABLE_FAVORITE_TEAM)
            val favorite = result.parseList(classParser<FavoriteTeam>())
            if(favorite.size < 1){
                adapter.notifyDataSetChanged()
                Toast.makeText(context, "Nothing Team Favorite!", Toast.LENGTH_SHORT).show()
            }
            else {
                favorites.addAll(favorite)
                adapter.notifyDataSetChanged()
                Log.d("dsadmamdka32131",favorites.toString())
            }
        }

    }

    override fun onResume() {
        super.onResume()
        showFavorite()
    }

}