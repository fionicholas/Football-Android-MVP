package com.github.fionicholas.football.ui.searchteam

import com.github.fionicholas.football.data.model.Team

interface SearchTeamView {
    fun showSearchTeam(data: List<Team>)
    fun onResponseNull()
    fun showLoading()
    fun hideLoading()
}