package com.hyun.worldwiser.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hyun.worldwiser.R
import com.hyun.worldwiser.model.Travel

class TravelAdapter(val context: Context, private val travelList: ArrayList<Travel>) : RecyclerView.Adapter<TravelAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_travel_list, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(travelList[position])
    }

    override fun getItemCount(): Int {
        return travelList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(travel: Travel) {

            val imageUrl = travel.imageUrl

            Glide.with(context)
                .load(imageUrl)
                .into(itemView.findViewById(R.id.iv_travel_imageUrl))
        }
    }
}