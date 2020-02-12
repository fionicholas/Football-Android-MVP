package com.github.fionicholas.football.ui.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.fionicholas.football.R
import com.github.fionicholas.football.data.model.Standing
import kotlinx.android.synthetic.main.item_standing.view.*

class StandingAdapter (private val standingList: List<Standing>)
    : RecyclerView.Adapter<StandingAdapter.StandingViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StandingViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_standing, parent, false)

        return StandingViewHolder(itemView)
    }

    override fun getItemCount(): Int = standingList.size

    override fun onBindViewHolder(holder: StandingViewHolder, position: Int) {
        holder.bindItem(standingList[position])
    }

    class StandingViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView) {


        val txtNameTeam = itemView.txt_name_team
        val txtPlayed = itemView.txt_played
        val txtWin = itemView.txt_win
        val txtLose = itemView.txt_lose
        val txtDraw = itemView.txt_draw
        val txtGm = itemView.txt_gm
        val txtGk = itemView.txt_gk


        fun bindItem(football: Standing){
            txtNameTeam.text = football.name
            txtPlayed.text = football.played
            txtWin.text = football.win
            txtLose.text = football.loss
            txtDraw.text = football.draw
            txtGm.text = football.goalsagainst
            txtGk.text = football.goalsfor

        }

    }
}