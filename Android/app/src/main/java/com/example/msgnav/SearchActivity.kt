package com.example.msgnav

import android.app.Activity
import android.os.Bundle
import android.view.View
import android.widget.Button
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

        finish()
    }

    override fun onDataChanged(data: Array<String>) {
        updateDoneButton(data)
    }

    fun updateDoneButton(data: Array<String>) {
        for (s: String in data) {
            if (s.isEmpty()) {
                doneButton.isEnabled = false
                return
            }
        }
        doneButton.isEnabled = true
    }
}