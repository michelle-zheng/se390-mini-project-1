package com.example.msgnav

import android.app.Activity
import android.app.PendingIntent
import android.content.Intent
import android.os.Bundle
import android.telephony.SmsManager
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class SearchActivity : Activity(), SearchRecyclerViewAdapter.OnDataChangedListener {
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: SearchRecyclerViewAdapter
    private lateinit var viewManager: RecyclerView.LayoutManager

    private lateinit var doneButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        val data: Array<String> = intent.getStringArrayExtra("locations") ?: Array(2) { i -> ""}

        viewManager = LinearLayoutManager(this)
        viewAdapter = SearchRecyclerViewAdapter(this, data, this)

        recyclerView = findViewById<RecyclerView>(R.id.search_recycler_view).apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
        }

        doneButton = findViewById(R.id.search_done_button)
        updateDoneButton(data)
    }

    fun onCloseButtonClick(view: View) {
        finish()
    }

    fun onDoneButtonClick(view: View) {
        // TODO: Send the SMS Message

        val locations: Array<String> = viewAdapter.getItems()
        sendSms(locations)

//        val intent = Intent(this, FullDirectionsActivity::class.java)
//
//        // TODO: Update this location value to the proper addresses returned by the server
//        intent.putExtra("locations", locations)
//        // TODO: Pass Array<Direction> as an extra to this activity (use name "directions")
//        intent.putExtra("directions", Array(20){ i -> Direction(R.drawable.search_icon,  "Turn left at Thomas Street Middle School", "100 m") })
//
//        if (this.intent.getBooleanExtra("new_activity", true)) {
//            startActivity(intent)
//        } else {
//            setResult(RESULT_OK, intent)
//        }
//
//        finish()
    }

    override fun onDataChanged(data: Array<String>) {
        updateDoneButton(data)
    }

    private fun updateDoneButton(data: Array<String>) {
        for (s: String in data) {
            if (s.isEmpty()) {
                doneButton.isEnabled = false
                return
            }
        }
        doneButton.isEnabled = true
    }

    private fun sendSms(data: Array<String>) {
        try {
            SmsManager.getDefault().sendTextMessage(
                "+13852090925",
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
}