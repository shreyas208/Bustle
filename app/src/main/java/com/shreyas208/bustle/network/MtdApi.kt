package com.shreyas208.bustle.network

import com.shreyas208.bustle.BuildConfig
import com.shreyas208.bustle.models.RoutesResponse
import com.shreyas208.bustle.models.StopsResponse
import io.reactivex.Flowable
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Query

interface MtdApiInterface {
    @GET("getroutes")
    fun getRoutes(
            @Query("key") apiKey: String
    ): Flowable<RoutesResponse>

    @GET("getstopsbysearch")
    fun getStopsBySearch(
            @Query("key") apiKey: String,
            @Query("query") query: String,
            @Query("count") count: Int
    ): Flowable<StopsResponse>
}

class MtdApiService {

    private val mtdApiInterface: MtdApiInterface

    companion object {
        private const val MTD_API_BASE_URL = "https://developer.cumtd.com/api/v2.2/json/"
    }

    init {
        val retrofit = Retrofit.Builder()
                .baseUrl(MTD_API_BASE_URL)
                .configure()
                .build()
        mtdApiInterface = retrofit.create(MtdApiInterface::class.java)
    }

    fun getStopsBySearch(query: String, count: Int): Flowable<StopsResponse> =
            mtdApiInterface.getStopsBySearch(BuildConfig.MTD_API_KEY, query, count).configure()
}