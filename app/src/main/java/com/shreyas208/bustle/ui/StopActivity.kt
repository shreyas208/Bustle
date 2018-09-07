package com.shreyas208.bustle.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.shreyas208.bustle.R
import com.shreyas208.bustle.models.Stop
import com.shreyas208.bustle.network.MtdApiService
import kotlinx.android.synthetic.main.activity_stop.*

class StopActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_STOP = "extra_stop"
    }

    private lateinit var stop: Stop
    private val mtdApiService = MtdApiService()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stop)

        stop = intent.getSerializableExtra(EXTRA_STOP) as Stop

        mtdApiService.getDeparturesByStop(stop.id, 60, 50)
                .withErrorHandling(this)
                .subscribe { departuresResponse ->
                    text.text = departuresResponse.departures.joinToString("\n") { stop -> stop.headsign }
                }
    }
}