package com.hyun.worldwiser.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hyun.worldwiser.R
import com.hyun.worldwiser.model.spots.Items
import com.hyun.worldwiser.model.spots.Root
import com.hyun.worldwiser.model.spots.SpotsItem

class TourSpotsAdapter(
    private val context: Context,
    private val spotsList: List<SpotsItem>
) : RecyclerView.Adapter<TourSpotsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_home_travel_spot_list, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return spotsList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val spotsItem = spotsList[position]
        holder.bind(spotsItem)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(spotsItem: SpotsItem) {

            Glide.with(itemView.context)
                .load(spotsItem.firstimage)
                .override(700, 500)
                .into(itemView.findViewById(R.id.iv_travel_spot_imageUrl))

            Log.d("TourSpotsAdapter", spotsItem.firstimage)
        }
    }
}