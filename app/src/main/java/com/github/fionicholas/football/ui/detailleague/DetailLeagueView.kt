package com.github.fionicholas.football.ui.detailleague

import com.github.fionicholas.football.data.model.DetailLeague

interface DetailLeagueView {
    fun showLeagueList(data: List<DetailLeague>)
    fun showLoading()
    fun hideLoading()
}