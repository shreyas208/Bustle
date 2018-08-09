package com.shreyas208.bustle.ui

import android.content.Context
import android.util.Log
import android.widget.ArrayAdapter
import com.shreyas208.bustle.models.Completion
import com.shreyas208.bustle.network.MtdAutocompleteService
import io.reactivex.Flowable

class AutocompleteAdapter(context: Context) : ArrayAdapter<String>(
        context,
        android.R.layout.simple_dropdown_item_1line) {

    private val mtdAutocompleteService = MtdAutocompleteService()

    fun setQuery(query: String) {
        clear()
        mtdAutocompleteService.search(query)
                .onErrorResumeNext { t: Throwable ->
                    Log.e("Bustle", t.message)
                    Flowable.empty<List<Completion>>()

                }
                .subscribe { completions ->
                    addAll(completions.map { toString() })
                }
    }

}