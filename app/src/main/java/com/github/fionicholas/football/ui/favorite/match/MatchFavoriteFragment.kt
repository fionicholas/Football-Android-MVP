package com.github.fionicholas.football.ui.favorite.match


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
import com.github.fionicholas.football.data.model.FavoriteMatch
import com.github.fionicholas.football.data.model.FavoriteTeam
import com.github.fionicholas.football.ui.detailmatch.DetailMatchActivity
import kotlinx.android.synthetic.main.fragment_match_favorite.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.select

/**
 * A simple [Fragment] subclass.
 */
class MatchFavoriteFragment : Fragment() {

    private var favorites: MutableList<FavoriteMatch> = mutableListOf()
    private lateinit var adapter: MatchFavoriteAdapter
    private lateinit var swipeRefresh: SwipeRefreshLayout
    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_match_favorite, container, false)

        recyclerView = rv_fav_match
        progressBar = loading
        swipeRefresh = swipe_layout

        showFavorite()
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        recyclerView = rv_fav_match
        progressBar = loading
        swipeRefresh = swipe_layout

        progressBar.visibility = View.VISIBLE

        adapter =
            MatchFavoriteAdapter(
                favorites
            ) {
                val intent = Intent(context, DetailMatchActivity::class.java)
                intent.putExtra("DETAIL_MATCH", it.eventId.toString())
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
        Handler().postDelayed(handler, 1000)

    }

    private fun showFavorite() {

        favorites.clear()
        context?.database?.use {
            swipeRefresh.isRefreshing = false
            val result = select(FavoriteMatch.TABLE_FAVORITE_MATCH)
            val favorite = result.parseList(classParser<FavoriteMatch>())
            if (favorite.size < 1) {
                adapter.notifyDataSetChanged()
                Toast.makeText(context, "Nothing Match Favorite!", Toast.LENGTH_SHORT).show()
            } else {
                favorites.addAll(favorite)
                adapter.notifyDataSetChanged()
                Log.d("dsadmamdka32131", favorites.toString())
            }
        }

    }

    override fun onResume() {
        super.onResume()
        showFavorite()
    }

}
