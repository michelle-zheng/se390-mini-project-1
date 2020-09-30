package com.example.msgnav

import android.Manifest
import android.app.Activity
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.telephony.SmsManager
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.Toast
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import java.util.*


class SearchActivity : Activity(), SearchRecyclerViewAdapter.OnDataChangedListener {
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: SearchRecyclerViewAdapter
    private lateinit var viewManager: RecyclerView.LayoutManager

    private lateinit var doneButton: Button
    private lateinit var currentLocationButton: Button

    private val broadcastReceiver: BroadcastReceiver = SmsReceiver()
    private lateinit var fusedLocationClient: FusedLocationProviderClient


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        setContext(this)
        val data: Array<String> = intent.getStringArrayExtra("locations") ?: Array(2) { i -> ""}

        viewManager = LinearLayoutManager(this)
        viewAdapter = SearchRecyclerViewAdapter(this, data, this)

        recyclerView = findViewById<RecyclerView>(R.id.search_recycler_view).apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
        }

        doneButton = findViewById(R.id.search_done_button)
        currentLocationButton = findViewById(R.id.search_current_location_button)
        updateDoneButton(data)

        val filter = IntentFilter().apply {
            addAction("android.provider.Telephony.SMS_RECEIVED")
        }
        registerReceiver(broadcastReceiver, filter)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
    }

    override fun onDestroy() {
        super.onDestroy()

        unregisterReceiver(broadcastReceiver)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        for (i in permissions.indices) {
            if (permissions[i] == Manifest.permission.ACCESS_FINE_LOCATION && grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                updateCurrentLocation()
            }
        }
    }

    fun onCloseButtonClick(view: View) {
        finish()
    }

    fun onDoneButtonClick(view: View) {
        // TODO: Add Mode to the SMS Message

        val imm: InputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)

        val locations: Array<String> = viewAdapter.getItems()

        var currentLocationIndex = -1;
        for (i in locations.indices) {
            if (locations[i].toLowerCase(Locale.ROOT) == "current location") {
                currentLocationIndex = i;
                break;
            }
        }

        if (currentLocationIndex != -1) {
            fusedLocationClient.lastLocation
                .addOnSuccessListener { location : Location? ->
                    if (location != null) {
                        locations[currentLocationIndex] = location.latitude.toString() + "," + location.longitude.toString()
                        sendSms(locations)
                    } else {
                        Toast.makeText(this, "Location could not be fetched", Toast.LENGTH_SHORT).show()
                    }
                }
        } else {
            sendSms(locations)
        }
    }

    fun onCurrentLocationButtonClick(view: View) {
        val locationPermission = ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
        if (locationPermission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 1)
        } else {
            updateCurrentLocation()
        }
    }

    override fun onDataChanged(data: Array<String>) {
        updateDoneButton(data)
        updateCurrentLocationButton(data)
    }

    private fun updateDoneButton(data: Array<String>) {
        for (s: String in data) {
            if (s.isBlank()) {
                doneButton.isEnabled = false
                return
            }
        }
        doneButton.isEnabled = true
    }

    private fun updateCurrentLocation() {
        for (i in 0 until viewManager.childCount) {
            if (viewManager.focusedChild?.equals(viewManager.getChildAt(i)) == true) {
                viewAdapter.onCurrentLocationButtonClick(i)
                viewManager.focusedChild?.clearFocus()
                if (i == viewManager.childCount - 1) viewManager.getChildAt(0)?.requestFocus()
                else viewManager.getChildAt(i+1)?.requestFocus()
                return
            }
        }
    }

    private fun updateCurrentLocationButton(data: Array<String>) {
        for (s: String in data) {
            if (s.toLowerCase(Locale.ROOT) == "current location") {
                currentLocationButton.visibility = GONE
                return
            }
        }
        currentLocationButton.visibility = VISIBLE
    }

    private fun sendSms(data: Array<String>) {
        try {
            SmsManager.getDefault().sendTextMessage(
//                "+13852090925",
                "+16478775992",
                null,
                "from " + data[0] + " to " + data[1],
                null,
                null
            )
        } catch (e: Exception) {
            Toast.makeText(
                this,
                "SMS Failed to Send, Please try again",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
    companion object {
        private lateinit var context: Context

        fun setContext(ctx: Context) {
            context=ctx
        }

        fun displayDirections(locations: Array<String>, directions: Array<Direction>) {
            Toast.makeText(context, "COMPANION OBJECT", Toast.LENGTH_LONG)
            val intent = Intent(context, FullDirectionsActivity::class.java)
            intent.putExtra("locations", locations)
            intent.putExtra("directions",  directions)
            context.startActivity(intent)
        }

    }
}