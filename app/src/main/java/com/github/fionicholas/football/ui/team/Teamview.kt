package com.github.fionicholas.football.ui.team

import com.github.fionicholas.football.data.model.Team

interface Teamview {

    fun showTeam(data: List<Team>)
    fun showLoading()
    fun hideLoading()
}