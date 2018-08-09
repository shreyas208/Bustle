package com.shreyas208.bustle.models

import com.google.gson.annotations.SerializedName

data class Stop(
        @SerializedName("stop_id") val id: String,
        @SerializedName("stop_name") val name: String,
        @SerializedName("stop_points") val boardingPoints: List<BoardingPoint>,
        val code: String
)

data class BoardingPoint(
        @SerializedName("stop_id") val id: String,
        @SerializedName("stop_lat") val lat: Float,
        @SerializedName("stop_lon") val lon: Float,
        @SerializedName("stop_name") val name: String,
        val code: String
)

data class StopsResponse(
        val stops: List<Stop>
)