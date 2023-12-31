package com.hyun.worldwiser.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hyun.worldwiser.R
import com.hyun.worldwiser.model.Travel
import com.hyun.worldwiser.ui.schedule.ScheduleActivity

class TravelAdapter(val context: Context, private val travelList: ArrayList<Travel>) : RecyclerView.Adapter<TravelAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_travel_list, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(travelList[position])

        holder.itemView.setOnClickListener {
            val intent = Intent(context, ScheduleActivity::class.java)
            intent.putExtra("country", holder.itemView.findViewById<TextView>(R.id.tv_travel_country).text.toString())
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return travelList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(travel: Travel) {

            val imageUrl = travel.imageUrl
            val country = travel.country

            itemView.findViewById<TextView>(R.id.tv_travel_country).text = country

            Glide.with(context)
                .load(imageUrl)
                .into(itemView.findViewById(R.id.iv_travel_imageUrl))
        }
    }
}