package com.github.fionicholas.football.ui.standing

import com.github.fionicholas.football.data.model.Standing

interface StandingView {

    fun showStanding(data: List<Standing>)
    fun showLoading()
    fun hideLoading()
}