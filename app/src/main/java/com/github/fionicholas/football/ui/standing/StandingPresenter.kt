package com.github.fionicholas.football.ui.standing

import android.util.Log
import com.github.fionicholas.football.data.model.FootballResponse
import com.github.fionicholas.football.network.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class StandingPresenter( private val view: StandingView) {

    fun getStanding(id: String){
        view.showLoading()
        val leagueServices = ApiClient().create()
        leagueServices.getStanding(id).enqueue(object : Callback<FootballResponse> {

            override fun onResponse(call: Call<FootballResponse>, response: Response<FootballResponse>) {

                if (response.isSuccessful) {
                    if (response.body() != null) {
                        view.hideLoading()
                        view.showStanding(response.body()!!.table)
                    }else {
                        view.hideLoading()
                        Log.d("tag", "failed")
                    }

                }else {
                    view.hideLoading()
                    Log.d("tag", "failed")
                }
            }

            override fun onFailure(call: Call<FootballResponse>, error: Throwable) {
                view.hideLoading()
                Log.e("tag", "error message : ${error.message}")
            }
        })
    }
}