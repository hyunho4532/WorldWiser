package com.hyun.worldwiser.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hyun.worldwiser.R

class TravelRecommendDetailImageSlideAdapter(val context: Context, val sliderImage: Array<String>) : RecyclerView.Adapter<TravelRecommendDetailImageSlideAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_travel_recommend_detail_list, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindSliderImage(sliderImage[position])
    }

    override fun getItemCount(): Int {
        return sliderImage.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageSlider: ImageView = itemView.findViewById(R.id.imageSlider)

        fun bindSliderImage(imageUrl: String) {
            Glide.with(context)
                .load(imageUrl)
                .into(imageSlider)
        }
    }
}