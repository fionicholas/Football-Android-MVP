package com.github.fionicholas.football.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class FootballResponse(
    @SerializedName("leagues")
    @Expose
    val results: List<DetailLeague>,

    @SerializedName("events")
    @Expose
    val events: List<Match>,

    @SerializedName("event")
    @Expose
    val event: List<Match>,

    @SerializedName("teams")
    @Expose
    val teams: List<Team>,

    @SerializedName("table")
    @Expose
    val table: List<Standing>
)