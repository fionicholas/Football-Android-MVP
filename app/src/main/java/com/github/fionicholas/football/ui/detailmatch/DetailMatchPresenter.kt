package com.github.fionicholas.football.ui.detailmatch

import android.util.Log
import com.github.fionicholas.football.data.model.FootballResponse
import com.github.fionicholas.football.network.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailMatchPresenter (private val view: DetailMatchView) {

    fun getDetailMatch(id: String){
        view.showLoading()
        val leagueServices = ApiClient().create()
        leagueServices.getDetailMatch(id).enqueue(object : Callback<FootballResponse> {

            override fun onResponse(call: Call<FootballResponse>, response: Response<FootballResponse>) {

                if (response.isSuccessful) {
                    if (response.body() != null) {
                        view.hideLoading()
                        response.body()?.events?.let { view.showDetailMatch(it) }
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
                Log.e("tag", "error message :  ${error.message}")
            }
        })
    }


    fun getHomeBadge(id: String){
        view.showLoading()
        val leagueServices = ApiClient().create()
        leagueServices.getTeamDetail(id).enqueue(object : Callback<FootballResponse> {

            override fun onResponse(call: Call<FootballResponse>, response: Response<FootballResponse>) {

                if (response.isSuccessful) {
                    if (response.body() != null) {
                        view.hideLoading()
                        response.body()?.teams?.let { view.showHomeBadge(it) }
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

    fun getAwayBadge(id: String){
        view.showLoading()
        val leagueServices = ApiClient().create()
        leagueServices.getTeamDetail(id).enqueue(object : Callback<FootballResponse> {

            override fun onResponse(call: Call<FootballResponse>, response: Response<FootballResponse>) {

                if (response.isSuccessful) {
                    if (response.body() != null) {
                        view.hideLoading()
                        response.body()?.teams?.let { view.showAwayBadge(it) }
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