package com.github.fionicholas.football.ui.searchmatch

import com.github.fionicholas.football.data.model.Match

interface SearchMatchView {

    fun showSearchMatch(data: List<Match>)
    fun onResponseNull()
    fun showLoading()
    fun hideLoading()
}