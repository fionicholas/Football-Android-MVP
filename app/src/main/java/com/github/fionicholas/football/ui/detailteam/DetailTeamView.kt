package com.github.fionicholas.football.ui.detailteam

import com.github.fionicholas.football.data.model.Team

interface DetailTeamView {

    fun showDetailTeam(data: List<Team>)
    fun showLoading()
    fun hideLoading()
}