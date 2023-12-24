package com.hyun.worldwiser.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hyun.worldwiser.R
import com.hyun.worldwiser.model.Country
import com.hyun.worldwiser.util.SnackBarFilter

class CountryAdapter(val context: Context, private val countryList: ArrayList<Country>) : RecyclerView.Adapter<CountryAdapter.ViewHolder>() {

    private val snackBarFilter: SnackBarFilter = SnackBarFilter()

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

            itemView.findViewById<ImageView>(R.id.iv_country_remove).setOnClickListener { view ->

                val position = adapterPosition

                if (position != RecyclerView.NO_POSITION) {
                    countryList.removeAt(position)
                    notifyItemRemoved(position)

                    snackBarFilter.removeCountrySnackBar(view, countryTitle.text.toString())
                }
            }
        }
    }
}