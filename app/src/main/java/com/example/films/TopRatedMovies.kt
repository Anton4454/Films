package com.example.films

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TopRatedMovies (
    @SerializedName("id") val id : String,
    @SerializedName("chartRating") val chartRating : Double
) : Parcelable