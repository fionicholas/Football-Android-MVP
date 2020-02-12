package com.github.fionicholas.football.ui.match

import com.github.fionicholas.football.data.model.Match

interface MatchView {

    fun showMatch(data: List<Match>)
    fun showLoading()
    fun hideLoading()

}