package com.hyun.worldwiser.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.hyun.worldwiser.R

class ImageAdapter (
    private val imageUrls: List<String>,
    private val eventListener: (String) -> Unit
    ) : RecyclerView.Adapter<ImageAdapter.ImageViewHolder>() {

    private var selectedPosition = RecyclerView.NO_POSITION

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
            eventListener.invoke(imageUrl)

            Log.d("ImageAdapter", "클릭")

            val previousSelected = selectedPosition
            selectedPosition = holder.adapterPosition

            notifyItemChanged(previousSelected)

            notifyItemChanged(selectedPosition)
        }


        if (position == selectedPosition) {
            holder.itemView.findViewById<ImageView>(R.id.iv_country_theme_status).visibility = View.VISIBLE
        } else {
            holder.itemView.findViewById<ImageView>(R.id.iv_country_theme_status).visibility = View.INVISIBLE
        }
    }

    override fun getItemCount(): Int {
        return imageUrls.size
    }

    inner class ImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ivCountryTheme: ImageView = itemView.findViewById(R.id.iv_country_theme)
    }
}