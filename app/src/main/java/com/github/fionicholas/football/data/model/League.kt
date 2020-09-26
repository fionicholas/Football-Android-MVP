package com.github.fionicholas.football.data.model

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class League (
    val id: String,
    val name: String,
    val description: String,
    val image: Int
) : Parcelable