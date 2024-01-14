package com.hyun.worldwiser.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.hyun.worldwiser.R
import com.hyun.worldwiser.model.TravelRecommend

class TravelRecommendAdapter(private val travelRecommendList: ArrayList<TravelRecommend>) :
    RecyclerView.Adapter<TravelRecommendAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_travel_recommend_list, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(travelRecommendList[position])
    }

    override fun getItemCount(): Int {
        return travelRecommendList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(travelRecommend: TravelRecommend) {
            val travelRecommendCountry = travelRecommend.travelRecommendCountry

            itemView.findViewById<TextView>(R.id.tv_travel_recommend_country).text = travelRecommendCountry
        }
    }
}