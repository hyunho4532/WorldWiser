package com.hyun.worldwiser.adapter

import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.hyun.worldwiser.R
import com.hyun.worldwiser.model.TravelDay
import com.hyun.worldwiser.viewmodel.ScheduleDayViewModel

class TravelDayAdapter(private val travelList: List<TravelDay>, private val scheduleDayViewModel: ScheduleDayViewModel) : RecyclerView.Adapter<TravelDayAdapter.ViewHolder>() {

    private var selectedPosition = RecyclerView.NO_POSITION

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_travel_day_list, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(travelList[position])

        if (position == selectedPosition) {
            holder.itemView.setBackgroundColor(Color.GRAY)
            holder.itemView.findViewById<TextView>(R.id.travel_day_count).setTextColor(Color.BLACK)

            scheduleDayViewModel.setSelectedItem(holder.itemView.findViewById<TextView>(R.id.travel_day_count).text.toString())

        } else {
            holder.itemView.setBackgroundColor(Color.WHITE)
        }
    }

    override fun getItemCount(): Int {
        return travelList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(travelDay: TravelDay) {

            val dayDifferenceCount = travelDay.dayDifference

            itemView.findViewById<TextView>(R.id.travel_day_count).text = dayDifferenceCount.toString() + "일"
        }

        init {
            itemView.setOnClickListener {
                val previousSelected = selectedPosition
                selectedPosition = adapterPosition

                // 이전에 선택된 아이템의 배경 색상 초기화
                notifyItemChanged(previousSelected)

                // 현재 선택한 아이템의 배경 색상 변경
                notifyItemChanged(selectedPosition)
            }
        }
    }
}