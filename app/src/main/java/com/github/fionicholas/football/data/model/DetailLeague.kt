package com.github.fionicholas.football.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class DetailLeague (

    @SerializedName("idLeague")
    @Expose
    val id: String?,

    @SerializedName("strLeague")
    @Expose
    val name: String?,

    @SerializedName("strDescriptionEN")
    @Expose
    val description: String?,

    @SerializedName("dateFirstEvent")
    @Expose
    val firstEvent: String?,

    @SerializedName("strCountry")
    @Expose
    val country: String?,

    @SerializedName("strGender")
    @Expose
    val gender: String?,

    @SerializedName("strBadge")
    @Expose
    val image: String?)