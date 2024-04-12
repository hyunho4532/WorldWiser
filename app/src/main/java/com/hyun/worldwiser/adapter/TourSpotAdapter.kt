package com.hyun.worldwiser.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hyun.worldwiser.R
import com.hyun.worldwiser.model.TourSpot

class TourSpotAdapter : RecyclerView.Adapter<TourSpotAdapter.ViewHolder>() {

    private var tourSpots: List<TourSpot> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TourSpotAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_home_travel_spot_list, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: TourSpotAdapter.ViewHolder, position: Int) {
        val tourSpot = tourSpots[position]
        holder.bind(tourSpot)
    }

    override fun getItemCount(): Int {
        return tourSpots.size
    }

    fun setData(tourSpots: List<TourSpot>) {
        this.tourSpots = tourSpots
        notifyDataSetChanged()
    }

    class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        fun bind(tourSpot: TourSpot) {
            itemView.apply {
                Glide.with(itemView.context)
                    .load(tourSpot.imageUrl)
                    .into(itemView.findViewById(R.id.iv_travel_spot_imageUrl))
            }
        }
    }
}