package com.github.fionicholas.football.ui.searchteam

import android.util.Log
import com.github.fionicholas.football.data.model.FootballResponse
import com.github.fionicholas.football.network.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchTeamPresenter (private val view : SearchTeamView) {

    fun getSearchTeam(id: String){
        view.showLoading()
        val leagueServices = ApiClient().create()
        leagueServices.getSearchTeam(id).enqueue(object : Callback<FootballResponse> {

            override fun onResponse(call: Call<FootballResponse>, response: Response<FootballResponse>) {

                if (response.isSuccessful) {
                    if (response.body()?.teams != null) {
                        view.hideLoading()
                        view.showSearchTeam(response.body()!!.teams)
                    }else {
                        view.hideLoading()
                        view.onResponseNull()
                        Log.d("tag", "gagal")
                    }

                }else {
                    view.hideLoading()
                    Log.d("tag", "gagal")
                }
            }

            override fun onFailure(call: Call<FootballResponse>, error: Throwable) {
                view.hideLoading()
                Log.e("tag", "errornya ${error.message}")
            }
        })
    }
}