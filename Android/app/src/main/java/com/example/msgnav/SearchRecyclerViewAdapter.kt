package com.example.msgnav

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.EditText
import androidx.recyclerview.widget.RecyclerView

class SearchRecyclerViewAdapter(private val context: Context, private val data: Array<String>, private val listener: OnDataChangedListener) :
    RecyclerView.Adapter<SearchRecyclerViewAdapter.ViewHolder>() {
    interface OnDataChangedListener {
        fun onDataChanged(data: Array<String>)
    }

    var isInitialized: Boolean = false

    class ViewHolder(val editText: EditText) : RecyclerView.ViewHolder(editText)

    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): ViewHolder {
        val editText = LayoutInflater.from(parent.context)
            .inflate(R.layout.edit_text, parent, false) as EditText

        return ViewHolder(editText)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.editText.setText(data[position])

        if (position == 0) {
            if (!isInitialized) {
                holder.editText.requestFocus()
                isInitialized = true;
            }
            holder.editText.hint = "Start"
            holder.editText.setCompoundDrawablesWithIntrinsicBounds(context.getDrawable(R.drawable.trip_origin_icon), null, null, null)
        } else {
            holder.editText.hint = "Destination"
            holder.editText.setCompoundDrawablesWithIntrinsicBounds(context.getDrawable(R.drawable.trip_destination_icon), null, null, null)
        }

        holder.editText.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                // no-op
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // no-op
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                data[holder.adapterPosition] = s.toString()
                listener.onDataChanged(data)
            }
        })
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = data.size

    fun getItems(): Array<String> {
        return data
    }

    fun onCurrentLocationButtonClick(position: Int) {
        data[position] = "Current Location"
        notifyItemChanged(position)

        listener.onDataChanged(data)
    }
}