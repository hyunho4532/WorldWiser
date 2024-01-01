package com.hyun.worldwiser.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.hyun.worldwiser.R
import com.hyun.worldwiser.model.CountryRanking

class CountryRankingAdapter(val context: Context, val countryRankingList : ArrayList<CountryRanking>) : RecyclerView.Adapter<CountryRankingAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_country_ranking_list, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(countryRankingList[position])
    }

    override fun getItemCount(): Int {
        return countryRankingList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(countryRanking: CountryRanking) {
            val countryRankingText = countryRanking.countryRanking

            itemView.findViewById<TextView>(R.id.tv_country_ranking).text = countryRankingText
        }
    }
}