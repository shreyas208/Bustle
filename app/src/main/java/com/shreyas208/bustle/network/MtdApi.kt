package com.shreyas208.bustle.network

import com.shreyas208.bustle.BuildConfig
import com.shreyas208.bustle.models.DeparturesResponse
import com.shreyas208.bustle.models.RoutesResponse
import com.shreyas208.bustle.models.StopsResponse
import io.reactivex.Flowable
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Query

interface MtdApiInterface {
    @GET("getroutes")
    fun getRoutes(
            @Query("key") apiKey: String = BuildConfig.MTD_API_KEY
    ): Flowable<RoutesResponse>

    @GET("getstopsbysearch")
    fun getStopsBySearch(
            @Query("query") query: String,
            @Query("count") count: Int,
            @Query("key") apiKey: String = BuildConfig.MTD_API_KEY
    ): Flowable<StopsResponse>

    @GET("getstopsbylatlon")
    fun getStopsByLatLon(
            @Query("lat") lat: Double,
            @Query("lon") lon: Double,
            @Query("count") count: Int,
            @Query("key") apiKey: String = BuildConfig.MTD_API_KEY
    ): Flowable<StopsResponse>

    @GET("getdeparturesbystop")
    fun getDeparturesByStop(
            @Query("stop_id") stopId: String,
            @Query("pt") previewTime: Int,
            @Query("count") count: Int,
            @Query("key") apiKey: String = BuildConfig.MTD_API_KEY
    ): Flowable<DeparturesResponse>
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
            mtdApiInterface.getStopsBySearch(query, count).configure()

    fun getStopsByLatLon(lat: Double, lon: Double, count: Int): Flowable<StopsResponse> =
            mtdApiInterface.getStopsByLatLon(lat, lon, count).configure()

    fun getDeparturesByStop(stopId: String, previewTime: Int, count: Int): Flowable<DeparturesResponse> =
            mtdApiInterface.getDeparturesByStop(stopId, previewTime, count).configure()
}