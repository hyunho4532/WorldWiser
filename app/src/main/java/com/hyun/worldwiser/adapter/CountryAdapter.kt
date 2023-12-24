package com.hyun.worldwiser.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.hyun.worldwiser.R
import com.hyun.worldwiser.model.Country

class CountryAdapter(val context: Context, private val countryList: ArrayList<Country>) : RecyclerView.Adapter<CountryAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_country_favorite_list, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return countryList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(countryList[position])
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val countryTitle: TextView = itemView.findViewById<TextView>(R.id.country)

        fun bind(country: Country) {
            countryTitle.text = country.countryFavorite
        }
    }
}