package com.example.msgnav

import android.content.Context

import android.view.LayoutInflater
import android.view.View
import android.view.View.*
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class FullDirectionsRecyclerViewAdapter(private val context: Context, private var data: Array<Direction>) :
    RecyclerView.Adapter<FullDirectionsRecyclerViewAdapter.ViewHolder>() {

    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.direction_cell, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val iconImageView: ImageView = holder.view.findViewById(R.id.direction_cell_icon)
        val directionTextView: TextView = holder.view.findViewById(R.id.direction_cell_direction_textview)
        val distanceTextView: TextView = holder.view.findViewById(R.id.direction_cell_distance_textview)
        val dividerView: View = holder.view.findViewById(R.id.direction_cell_divider_view)

        iconImageView.setImageDrawable(context.getDrawable(data[position].icon))
        directionTextView.text = data[position].directionText
        distanceTextView.text = data[position].distance
        distanceTextView.visibility = if (data[position].distance.isEmpty()) GONE else VISIBLE
        dividerView.visibility = if (position == data.size - 1) GONE else VISIBLE
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = data.size

    fun setData(data: Array<Direction>) {
        this.data = data
        notifyDataSetChanged()
    }
}