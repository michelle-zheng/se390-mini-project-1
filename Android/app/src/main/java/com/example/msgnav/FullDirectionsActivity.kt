package com.example.msgnav

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class FullDirectionsActivity : Activity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: FullDirectionsRecyclerViewAdapter
    private lateinit var viewManager: RecyclerView.LayoutManager

    private lateinit var originTextView: TextView
    private lateinit var destinationTextView: TextView

    private lateinit var locations: Array<String>
    private lateinit var directions: Array<Direction>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_full_directions)

        locations = getLocations(intent)
        directions = getDirections(intent)

        val headerView: View = findViewById(R.id.full_directions_header_view)
        headerView.setOnClickListener {
            val intent = Intent(this, SearchActivity::class.java)
            intent.putExtra("locations", locations)
            intent.putExtra("new_activity", false)
            startActivityForResult(intent, 0)
        }

        originTextView = findViewById(R.id.full_directions_origin_textview)
        destinationTextView = findViewById(R.id.full_directions_destination_textview)

        updateHeader()

        viewManager = LinearLayoutManager(this)
        viewAdapter = FullDirectionsRecyclerViewAdapter(this, directions)

        recyclerView = findViewById<RecyclerView>(R.id.full_directions_recycler_view).apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 0 && resultCode == RESULT_OK) {
            locations = getLocations(data)
            directions = getDirections(data)
            updateHeader()
            viewAdapter.setData(directions)
        }
    }

    private fun updateHeader() {
        originTextView.text = locations[0]
        destinationTextView.text = locations.last()
    }

    private fun getLocations(intent: Intent?): Array<String> {
        return intent?.getStringArrayExtra("locations") ?: emptyArray()
    }

    private fun getDirections(intent: Intent?): Array<Direction> {
        return intent?.getParcelableArrayExtra("directions")?.map { it as Direction }?.toTypedArray() ?: emptyArray()
    }
}