package com.hyun.worldwiser.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import com.hyun.worldwiser.R
import com.hyun.worldwiser.model.Schedule

class ScheduleAdapter(val context: Context, private val scheduleList: ArrayList<Schedule>) : RecyclerView.Adapter<ScheduleAdapter.ViewHolder>() {

    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_travel_schedule_list, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(scheduleList[position])
    }

    override fun getItemCount(): Int {
        return scheduleList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(schedule: Schedule) {
            val todo = schedule.todo
            val todoDate = schedule.todoDate
            val status = schedule.status
            val category = schedule.category

            itemView.findViewById<TextView>(R.id.tv_travel_schedule_todo).text = todo
            itemView.findViewById<TextView>(R.id.tv_travel_schedule_todoDate).text = todoDate
            itemView.findViewById<TextView>(R.id.tv_travel_schedule_category).text = category
            itemView.findViewById<TextView>(R.id.tv_travel_schedule_status).text = status
        }
    }
}