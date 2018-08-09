package com.shreyas208.bustle.network

import com.shreyas208.bustle.models.Completion
import io.reactivex.Flowable
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Query

interface MtdAutocompleteInterface {
    @GET("search")
    fun search(
            @Query("query") query : String
    ) : Flowable<List<Completion>>
}

class MtdAutocompleteService {

    private val mtdAutocompleteInterface : MtdAutocompleteInterface

    companion object {
        private const val MTD_AUTOCOMPLETE_BASE_URL = "https://www.cumtd.com/autocomplete/stops/v1.0/json/"
    }

    init {
        val retrofit = Retrofit.Builder()
                .baseUrl(MTD_AUTOCOMPLETE_BASE_URL)
                .configure()
                .build()
        mtdAutocompleteInterface = retrofit.create(MtdAutocompleteInterface::class.java)
    }

    fun search(query: String) : Flowable<List<Completion>> =
            mtdAutocompleteInterface.search(query).configure()
}