package com.github.fionicholas.football.ui.favorite.team

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.fionicholas.football.R
import com.github.fionicholas.football.data.model.FavoriteTeam
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_team.view.*

class TeamFavoriteAdapter(private val teamList: List<FavoriteTeam>, private val listener: (FavoriteTeam) -> Unit) :
    RecyclerView.Adapter<TeamFavoriteAdapter.TeamViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeamViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_team, parent, false)

        return TeamViewHolder(itemView)
    }

    override fun getItemCount(): Int = teamList.size

    override fun onBindViewHolder(holder: TeamViewHolder, position: Int) {
        holder.bindItem(teamList[position], listener)
    }

    class TeamViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {


        val ivPhoto = itemView.img_item_photo
        val tvNameTeam = itemView.tv_name
        val tvDesc = itemView.tv_deskripsi
        val parent = itemView.card_view


        fun bindItem(football: FavoriteTeam, listener: (FavoriteTeam) -> Unit) {
            tvNameTeam.text = football.nameTeam
            tvDesc.text = football.descTeam
            Picasso.get().load(football.badgeTeam).into(ivPhoto)

            parent.setOnClickListener {
                listener(football)
            }

        }

    }
}