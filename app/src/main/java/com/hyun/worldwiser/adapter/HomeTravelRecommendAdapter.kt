package com.hyun.worldwiser.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.hyun.worldwiser.R
import com.hyun.worldwiser.model.HomeTravelRecommend

class HomeTravelRecommendAdapter (
    private val context: android.content.Context,
    private val homeRecommendList : ArrayList<HomeTravelRecommend>
) : RecyclerView.Adapter<HomeTravelRecommendAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_home_travel_recommend_list, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return homeRecommendList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(homeRecommendList[position])
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(homeTravelRecommend: HomeTravelRecommend) {
            itemView.findViewById<TextView>(R.id.tv_travel_recommend).text = homeTravelRecommend.travelRecommendCountry
            itemView.findViewById<TextView>(R.id.tv_nickname_recommend).text = homeTravelRecommend.travelRecommendNickname
        }
    }
}