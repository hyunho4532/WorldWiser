package com.hyun.worldwiser.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.hyun.worldwiser.R
import com.hyun.worldwiser.model.Travel
import com.hyun.worldwiser.ui.schedule.ScheduleActivity
import com.hyun.worldwiser.util.SnackBarFilter
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList

class TravelAdapter(val context: Context, private val travelList: ArrayList<Travel>, private val fireStore: FirebaseFirestore, private val auth: FirebaseAuth) : RecyclerView.Adapter<TravelAdapter.ViewHolder>() {

    private val snackBarFilter: SnackBarFilter = SnackBarFilter()

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

    fun removeItem(position: Int) {
        val removedTravelCountryItem = travelList[position]
        val country = removedTravelCountryItem.country

        val deleteQuery = fireStore.collection("travelInserts").whereEqualTo("country", country).whereEqualTo("authUid", auth.currentUser!!.uid)

        deleteQuery.get().addOnSuccessListener { documents ->
            for (document in documents) {
                fireStore.collection("travelInserts").document(document.id).delete()
                    .addOnSuccessListener {
                        travelList.removeAt(position)
                        notifyItemRemoved(position)

                        snackBarFilter.getTravelRemoveSnackBar()
                    }
                    .addOnFailureListener { error ->

                    }
            }
        }

        travelList.removeAt(position)
        notifyItemRemoved(position)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(travel: Travel) {

            val imageUrl = travel.imageUrl
            val country = travel.country
            val startDay = travel.startDay
            val endDay = travel.endDay

            itemView.findViewById<TextView>(R.id.tv_travel_country).text = country
            itemView.findViewById<TextView>(R.id.tv_travel_status).text = startDay

            val calendar: Calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = (calendar.get(Calendar.MONTH) + 1)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            val todayDateText = "$year-${String.format("%02d", month)}-${String.format("%02d", day)}"

            val todayDate = LocalDate.parse(todayDateText, DateTimeFormatter.ISO_DATE)
            val startDate = LocalDate.parse(startDay, DateTimeFormatter.ISO_DATE)
            val endDate = LocalDate.parse(endDay, DateTimeFormatter.ISO_DATE)

            // isAfter(Date date): 주어진 날짜가, 파라미터로 전달받은 날짜보다 클 경우 true 리턴
            // isBefore(Date date): 주어진 날짜가, 파라미터로 전달받은 날짜보다 작을 경우 true 리턴
            // isEqual(Date date): 주어진 날짜가, 파라미터로 전달받은 날짜보다 작을 경우 true 리턴

            // startDate: 시작 날짜
            // endDate: 끝 날짜
            if (startDate.isAfter(todayDate)) {
                itemView.findViewById<TextView>(R.id.tv_travel_status).text = "여행 예정"
            } else if (todayDate.isEqual(startDate) || (todayDate.isAfter(startDate) && todayDate.isBefore(endDate))) {
                itemView.findViewById<TextView>(R.id.tv_travel_status).text = "현재 여행 중"
            } else if (todayDate.isEqual(endDate) || todayDate.isAfter(endDate)) {
                itemView.findViewById<TextView>(R.id.tv_travel_status).text = "여행 종료"
            }

            Glide.with(context)
                .load(imageUrl)
                .into(itemView.findViewById(R.id.iv_travel_imageUrl))
        }
    }
}