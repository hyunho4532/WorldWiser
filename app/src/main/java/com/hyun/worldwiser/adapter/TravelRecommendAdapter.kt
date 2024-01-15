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
import com.hyun.worldwiser.model.TravelRecommend

class TravelRecommendAdapter(private val context: Context, private val travelRecommendList: ArrayList<TravelRecommend>) :
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
            val travelRecommendImageUrl = travelRecommend.travelRecommendImageUrl

            Glide
                .with(context)
                .load(travelRecommendImageUrl)
                .into(itemView.findViewById(R.id.iv_travel_recommend_imageUrl))

            itemView.findViewById<TextView>(R.id.tv_travel_recommend_country).text = travelRecommendCountry
        }
    }
}