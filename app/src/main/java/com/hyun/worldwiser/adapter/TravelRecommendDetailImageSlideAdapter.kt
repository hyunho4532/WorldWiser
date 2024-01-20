package com.hyun.worldwiser.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hyun.worldwiser.R

class TravelRecommendDetailImageSlideAdapter(val context: Context, private val sliderImage: Array<String?>) : RecyclerView.Adapter<TravelRecommendDetailImageSlideAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_travel_recommend_detail_list, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindSliderImage(sliderImage[position])

        Log.d("TravelRecommendDetailImageSlideAdapter", sliderImage[position]!!.trim())

    }

    override fun getItemCount(): Int {
        return sliderImage.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val imageSlider: ImageView = itemView.findViewById(R.id.imageSlider)

        fun bindSliderImage(imageUrls: String?) {

            val imageUrlArray = imageUrls?.split(",")?.toTypedArray()

            if (!imageUrlArray.isNullOrEmpty()) {

                Log.d("imageUrlArraySize", imageUrlArray.size.toString())

                for (i in 0..imageUrlArray.size) {

                    Log.d("imageUrlForArraySize")

                    Glide.with(context)
                        .load(imageUrlArray[i].trim())
                        .into(imageSlider)
                }
            }
        }
    }
}