package com.shreyas208.bustle.models

import com.google.gson.annotations.SerializedName

data class Departure(
        @SerializedName("stop_id") val stopId: String,
        @SerializedName("headsign") val headsign: String,
        @SerializedName("route") val route: Route,
        @SerializedName("expected_mins") val expectedMins: Int
)

data class DeparturesResponse(
        val departures: List<Departure>
)