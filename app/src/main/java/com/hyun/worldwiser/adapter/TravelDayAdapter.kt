package com.hyun.worldwiser.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.hyun.worldwiser.R
import com.hyun.worldwiser.model.TravelDay

class TravelDayAdapter(private val travelList: List<TravelDay>) : RecyclerView.Adapter<TravelDayAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_travel_day_list, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(travelList[position])
    }

    override fun getItemCount(): Int {
        return travelList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(travelDay: TravelDay) {

            val dayDifferenceCount = travelDay.dayDifference

            itemView.findViewById<TextView>(R.id.travel_day_count).text = dayDifferenceCount.toString()
        }
    }
}