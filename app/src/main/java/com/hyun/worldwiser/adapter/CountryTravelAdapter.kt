package com.hyun.worldwiser.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.hyun.worldwiser.R
import com.hyun.worldwiser.model.CountryTravel

class CountryTravelAdapter(val context: Context, private val countryTravelList: ArrayList<CountryTravel>) : RecyclerView.Adapter<CountryTravelAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_travel_favorite_list, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(countryTravelList[position])
    }

    override fun getItemCount(): Int {
        return countryTravelList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val travelCountryTitle: TextView = itemView.findViewById(R.id.travel_country)

        fun bind(travelCountry: CountryTravel) {
            travelCountryTitle.text = travelCountry.countryTravel
        }
    }
}