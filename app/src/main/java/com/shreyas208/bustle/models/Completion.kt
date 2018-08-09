package com.shreyas208.bustle.models

import com.google.gson.annotations.SerializedName

data class Completion(
        @SerializedName("n") val name: String
) {
    override fun toString() = name
}