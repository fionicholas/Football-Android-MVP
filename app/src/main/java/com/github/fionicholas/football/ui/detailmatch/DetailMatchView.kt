package com.github.fionicholas.football.ui.detailmatch

import com.github.fionicholas.football.data.model.Match
import com.github.fionicholas.football.data.model.Team

interface DetailMatchView {
    fun showDetailMatch(data: List<Match>)
    fun showHomeBadge(data: List<Team>)
    fun showAwayBadge(data: List<Team>)
    fun showLoading()
    fun hideLoading()
}