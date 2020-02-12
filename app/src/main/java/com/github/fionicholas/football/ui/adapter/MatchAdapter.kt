package com.github.fionicholas.football.ui.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.fionicholas.football.R
import com.github.fionicholas.football.data.model.Match
import kotlinx.android.synthetic.main.item_match.view.*

class MatchAdapter (private val matchList: List<Match>, private val listener: (Match) -> Unit)
    : RecyclerView.Adapter<MatchAdapter.MatchViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MatchViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_match, parent, false)

        return MatchViewHolder(itemView)
    }

    override fun getItemCount(): Int = matchList.size

    override fun onBindViewHolder(holder: MatchViewHolder, position: Int) {
        holder.bindItem(matchList[position], listener)
    }

    class MatchViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView) {


        val txtNameLeague = itemView.tv_name_league
        val txtScoreAway = itemView.tv_score_away
        val txtScoreHome = itemView.tv_score_home
        val txtTeamHome = itemView.tv_team_home
        val txtTeamAway = itemView.tv_team_away
        val parent = itemView.card_view


        fun bindItem(football: Match, listener: (Match) -> Unit){
            txtNameLeague.text = football.nameLeague
            txtTeamHome.text = football.homeTeam
            txtTeamAway.text = football.awayTeam
            txtScoreHome.text = football.homeScore
            txtScoreAway.text = football.awayScore

            parent.setOnClickListener {
                listener(football)
            }

        }

    }
}