package com.hyun.worldwiser.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.hyun.worldwiser.R
import com.hyun.worldwiser.model.TravelStatus

class TravelStatusAdapter(val context: Context, private val travelStatusList: ArrayList<TravelStatus>) : RecyclerView.Adapter<TravelStatusAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_travel_status_list, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(travelStatusList[position])
    }

    override fun getItemCount(): Int {
        return travelStatusList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(travelStatus: TravelStatus) {
            val countryStatusText = travelStatus.countryStatus

            itemView.findViewById<TextView>(R.id.tv_travel_status).text = countryStatusText
        }
    }
}