package com.shreyas208.bustle.models

import com.google.gson.annotations.SerializedName

data class Route(
        @SerializedName("route_id") val id: String,
        @SerializedName("route_long_name") val longName: String,
        @SerializedName("route_short_name") val shortName: String,
        @SerializedName("route_color") val color: String,
        @SerializedName("route_text_color") val textColor: String
)

data class RoutesResponse(
        val routes: List<Route>
)