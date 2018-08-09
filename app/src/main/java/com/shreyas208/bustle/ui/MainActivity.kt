package com.shreyas208.bustle.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import com.shreyas208.bustle.R
import com.shreyas208.bustle.models.StopsResponse
import com.shreyas208.bustle.network.MtdApiService
import io.reactivex.Flowable
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), TextView.OnEditorActionListener {

    private val mtdApiService = MtdApiService()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setActionBar(findViewById(R.id.toolbar))

        searchField.setOnEditorActionListener(this)
        searchField.setAdapter(AutocompleteAdapter(this))
    }

    override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
        return when (actionId) {
            EditorInfo.IME_ACTION_SEARCH -> {
                onSearch(v?.text.toString())
                true
            }
            else -> false
        }
    }

    fun onSearch(query : String) {
        if (searchField.text.isNullOrEmpty()) {
            return showErrorToast(R.string.an_error_occured)
        }
        mtdApiService.getStopsBySearch(query, 5)
                .onErrorResumeNext { t: Throwable ->
                    Log.e("Bustle", t.message)
                    Flowable.empty<StopsResponse>()
                }
                .subscribe { stopsResponse ->
                    text.text = stopsResponse.stops.joinToString(
                            separator = "\n",
                            transform = { stop -> stop.name }
                    )
                }
    }
}