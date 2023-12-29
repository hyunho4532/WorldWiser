package com.hyun.worldwiser.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hyun.worldwiser.R

class ImageAdapter (
    private val imageUrls: List<String>,
    private val imageUrlListener: (String) -> Unit
    ) : RecyclerView.Adapter<ImageAdapter.ImageViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_country_theme_list, parent, false)

        return ImageViewHolder(view)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val imageUrl = imageUrls[position]

        Glide.with(holder.itemView.context)
            .load(imageUrl)
            .into(holder.ivCountryTheme)

        holder.itemView.setOnClickListener {
            imageUrlListener.invoke(imageUrl)
        }
    }

    override fun getItemCount(): Int {
        return imageUrls.size
    }

    class ImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ivCountryTheme: ImageView = itemView.findViewById(R.id.iv_country_theme)
    }
}