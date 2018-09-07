package com.shreyas208.bustle.ui

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.*
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.shreyas208.bustle.R
import com.shreyas208.bustle.models.Stop
import com.shreyas208.bustle.network.MtdApiService
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), TextView.OnEditorActionListener, NearbyStopAdapter.Callback {

    private val mtdApiService = MtdApiService()
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        initiateLocationRequest()

        nearbyRecycler.layoutManager = LinearLayoutManager(this)

        searchField.setOnEditorActionListener(this)
    }

    private fun initiateLocationRequest() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    1099)
            return
        }
        fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
            if (location != null) {
                mtdApiService.getStopsByLatLon(location.latitude, location.longitude, 10)
                        .withErrorHandling(this)
                        .subscribe { stopsResponse ->
                            nearbyRecycler.adapter = NearbyStopAdapter(stopsResponse.stops, this)
                        }
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            1099 -> {
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    initiateLocationRequest()
                }
            }
        }
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

    private fun onSearch(query : String) {
        if (searchField.text.isNullOrEmpty()) {
            return showToast(R.string.an_error_occured)
        }
        mtdApiService.getStopsBySearch(query, 5)
                .withErrorHandling(this)
                .subscribe { stopsResponse ->
                    text.text = stopsResponse.stops.joinToString("\n") { stop -> stop.name }
                }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.menu_item_settings -> startActivity(Intent(this, SettingsActivity::class.java))
        }
        return true
    }

    override fun onStopClicked(stop: Stop) {
        val intent = Intent(this, StopActivity::class.java)
        intent.putExtra(StopActivity.EXTRA_STOP, stop)
        startActivity(intent)
    }
}

class NearbyStopAdapter(private val stops: List<Stop>, private val callback: Callback) : RecyclerView.Adapter<NearbyStopAdapter.ViewHolder>() {

    interface Callback {
        fun onStopClicked(stop: Stop)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val textView = LayoutInflater.from(parent.context).inflate(android.R.layout.simple_list_item_1, parent, false) as TextView
        return ViewHolder(textView)
    }

    override fun getItemCount(): Int {
        return stops.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.textView.text = stops[position].name
        holder.textView.setOnClickListener{ _ -> callback.onStopClicked(stops[position]) }
    }

    class ViewHolder(val textView: TextView) : RecyclerView.ViewHolder(textView)
}