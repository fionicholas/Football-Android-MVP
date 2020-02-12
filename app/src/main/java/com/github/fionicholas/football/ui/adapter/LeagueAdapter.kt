package com.github.fionicholas.football.ui.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.fionicholas.football.R
import com.github.fionicholas.football.data.model.League
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_league.view.*

class LeagueAdapter(private val leagueList: List<League>, private val listener: (League) -> Unit)
    : RecyclerView.Adapter<LeagueAdapter.FootballViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FootballViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_league, parent, false)

        return FootballViewHolder(itemView)
    }

    override fun getItemCount(): Int = leagueList.size

    override fun onBindViewHolder(holder: FootballViewHolder, position: Int) {
        holder.bindItem(leagueList[position], listener)
    }

    class FootballViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView) {


        val txtName = itemView.tv_name
        val imgPhoto = itemView.iv_league

            fun bindItem(football: League, listener: (League) -> Unit){
                Picasso.get().load(football.image).into(imgPhoto)
                txtName.text = football.name

                imgPhoto.setOnClickListener {
                    listener(football)
                }



            }

    }
}
