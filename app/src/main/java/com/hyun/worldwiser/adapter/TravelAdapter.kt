package com.hyun.worldwiser.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.hyun.worldwiser.R
import com.hyun.worldwiser.databinding.FragmentProfileBinding
import com.hyun.worldwiser.model.Travel
import com.hyun.worldwiser.ui.schedule.ScheduleActivity
import com.hyun.worldwiser.viewmodel.TravelDeleteViewModel
import com.hyun.worldwiser.viewmodel.TravelStatusViewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList

class TravelAdapter(
    val context: Context,
    private val travelList: ArrayList<Travel>,
) : RecyclerView.Adapter<TravelAdapter.ViewHolder>() {

    private val travelDeleteViewModel: TravelDeleteViewModel = TravelDeleteViewModel()
    private val travelStatusViewModel: TravelStatusViewModel = TravelStatusViewModel()

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

        travelDeleteViewModel.travelDeleteViewModel(country) { isSuccess ->
            if (isSuccess) {
                travelList.removeAt(position)
                notifyItemRemoved(position)
            } else {
                print("삭제 실패")
            }
        }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(travel: Travel) {

            val imageUrl = travel.imageUrl
            val country = travel.country
            val startDay = travel.startDay
            val endDay = travel.endDay

            itemView.findViewById<TextView>(R.id.tv_travel_country).text = country.toString()
            itemView.findViewById<TextView>(R.id.tv_travel_status).text = startDay.toString()

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
            travelStatusViewModel.getTravelStatus(todayDate, startDate, endDate)
            itemView.findViewById<TextView>(R.id.tv_travel_status).text = travelStatusViewModel.travelStatus.toString()

            Glide.with(context)
                .load(imageUrl)
                .into(itemView.findViewById(R.id.iv_travel_imageUrl))
        }
    }
}