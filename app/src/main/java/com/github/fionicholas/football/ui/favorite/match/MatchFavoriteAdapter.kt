package com.github.fionicholas.football.ui.favorite.match

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.fionicholas.football.R
import com.github.fionicholas.football.data.model.FavoriteMatch
import kotlinx.android.synthetic.main.item_match.view.*

class MatchFavoriteAdapter (private val leagueList: List<FavoriteMatch>, private val listener: (FavoriteMatch) -> Unit)
    : RecyclerView.Adapter<MatchFavoriteAdapter.FootballViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FootballViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_match, parent, false)

        return FootballViewHolder(itemView)
    }

    override fun getItemCount(): Int = leagueList.size

    override fun onBindViewHolder(holder: FootballViewHolder, position: Int) {
        holder.bindItem(leagueList[position], listener)
    }

    class FootballViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView) {


        val txtNameLeague = itemView.tv_name_league
        val txtScoreAway = itemView.tv_score_away
        val txtScoreHome = itemView.tv_score_home
        val txtTeamHome = itemView.tv_team_home
        val txtTeamAway = itemView.tv_team_away
        val parent = itemView.card_view


        fun bindItem(football: FavoriteMatch, listener: (FavoriteMatch) -> Unit){
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