package com.github.fionicholas.football.network

import com.github.fionicholas.football.data.model.DetailLeague
import com.github.fionicholas.football.data.model.FootballResponse
import com.github.fionicholas.football.data.model.League
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiServices {
    @GET("lookupleague.php")
    fun getLeague(@Query("id") idLeague: String): Call <FootballResponse>

    @GET("eventspastleague.php")
    fun getMatch(@Query("id") idLeague: String): Call <FootballResponse>

    @GET("lookuptable.php")
    fun getStanding(@Query("l") id: String): Call <FootballResponse>

    @GET("lookup_all_teams.php")
    fun getTeam(@Query("id") id: String): Call <FootballResponse>

    @GET("lookupevent.php")
    fun getDetailMatch(@Query("id") id: String): Call <FootballResponse>

    @GET("lookupteam.php")
    fun getTeamDetail(@Query("id") id: String): Call <FootballResponse>

    @GET("searchevents.php")
    fun getSearchMatch(@Query("e") id: String): Call <FootballResponse>

    @GET("searchteams.php")
    fun getSearchTeam(@Query("t") id: String): Call <FootballResponse>



}