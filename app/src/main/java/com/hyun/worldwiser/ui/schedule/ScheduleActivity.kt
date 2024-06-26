package com.hyun.worldwiser.ui.schedule

import android.annotation.SuppressLint
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.appcompat.widget.AppCompatButton
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.hyun.worldwiser.R
import com.hyun.worldwiser.adapter.ScheduleAdapter
import com.hyun.worldwiser.adapter.TravelDayAdapter
import com.hyun.worldwiser.model.Schedule
import com.hyun.worldwiser.model.TravelDay
import com.hyun.worldwiser.util.SnackBarFilter
import com.hyun.worldwiser.viewmodel.ScheduleDayViewModel
import com.hyun.worldwiser.viewmodel.ScheduleStatusViewModel
import com.skydoves.powerspinner.PowerSpinnerView
import java.text.SimpleDateFormat
import java.util.*
import java.util.Calendar.getInstance
import kotlin.collections.ArrayList

class ScheduleActivity : AppCompatActivity() {

    private val db = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()
    private lateinit var context: Context

    private var travelDayList = mutableListOf<TravelDay>()

    private val snackBarFilter: SnackBarFilter = SnackBarFilter()

    private lateinit var country: String

    @SuppressLint("SetTextI18n", "NotifyDataSetChanged", "CutPasteId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_schedule)

        context = applicationContext

        val scheduleDayViewModel: ScheduleDayViewModel = ViewModelProvider(this)[ScheduleDayViewModel::class.java]
        val scheduleStatusViewModel: ScheduleStatusViewModel = ViewModelProvider(this)[ScheduleStatusViewModel::class.java]

        val rvScheduleDay : RecyclerView = findViewById(R.id.rv_schedule_day)

        val travelCountryIntent = intent.getStringExtra("country")

        val bottomSheetTravelScheduleView = layoutInflater.inflate(R.layout.dialog_bottom_sheet_travel_schedule, null)
        val bottomSheetTravelScheduleDatePickerView = layoutInflater.inflate(R.layout.dialog_bottom_sheet_travel_schedule_datepicker, null)

        val bottomSheetTravelScheduleDialog = BottomSheetDialog(this)
        val bottomSheetTravelScheduleDatePickerDialog = BottomSheetDialog(this)

        bottomSheetTravelScheduleDialog.setContentView(bottomSheetTravelScheduleView)
        bottomSheetTravelScheduleDatePickerDialog.setContentView(bottomSheetTravelScheduleDatePickerView)

        val tvTravelScheduleTime = bottomSheetTravelScheduleView.findViewById<TextView>(R.id.tv_travel_schedule_time)

        db.collection("travelInserts").whereEqualTo("authUid", auth.currentUser!!.uid).whereEqualTo("country", travelCountryIntent).get()
            .addOnSuccessListener { querySnapshot ->

                for (document in querySnapshot.documents) {
                    val imageUrl = document["imageUrl"].toString()
                    country = document["country"].toString()
                    val startDay = document["startDay"].toString()
                    val endDay = document["endDay"].toString()

                    findViewById<TextView>(R.id.tv_travel_schedule_country).text = country
                    findViewById<TextView>(R.id.tv_travel_schedule_calendar).text = startDay + " ~ " + endDay

                    Glide.with(context)
                        .load(imageUrl)
                        .into(findViewById(R.id.iv_travel_schedule_url))

                    val dayDifference = calculateDayDifference(startDay, endDay)

                    for (dayCount: Int in 1..dayDifference) {
                        val travelDay = TravelDay(dayCount)
                        travelDayList.add(travelDay)
                    }

                    val travelDayAdapter = TravelDayAdapter(travelDayList, scheduleDayViewModel, scheduleStatusViewModel)
                    rvScheduleDay.adapter = travelDayAdapter
                    rvScheduleDay.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
                }
            }

        val scheduleList: ArrayList<Schedule> = ArrayList()
        val recyclerView: RecyclerView = findViewById(R.id.rv_schedule_todo)

        val scheduleAdapter = ScheduleAdapter(context, scheduleList)

        recyclerView.adapter = scheduleAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        scheduleDayViewModel.selectedItem.observe(this) { selectedDay ->
            db.collection("plans")
                .whereEqualTo("authUid", auth.currentUser!!.uid)
                .whereEqualTo("country", travelCountryIntent)
                .whereEqualTo("todoDay", selectedDay)
                .get()
                .addOnSuccessListener { querySnapshot ->
                    scheduleList.clear() // 기존 데이터 모두 삭제

                    for (document in querySnapshot.documents) {
                        scheduleList.add(
                            Schedule(
                                document["todo"].toString(),
                                document["todoDate"].toString(),
                                document["category"].toString(),
                                document["status"].toString()
                            )
                        )
                    }

                    scheduleAdapter.notifyDataSetChanged() // 변경된 데이터를 RecyclerView에 반영
                }
        }

        db.collection("verifications").document(auth.currentUser!!.uid).get()
            .addOnSuccessListener { document ->

                val nickname = document["nickname"].toString()

                bottomSheetTravelScheduleView.findViewById<TimePicker>(R.id.tp_travel_schedule).setOnTimeChangedListener { timePicker, hour, minute ->

                    val formattedHour = if (hour >= 12) {
                        // 12시간 형식으로 변환
                        if (hour > 12) hour - 12 else hour
                    } else {
                        // 0시를 12시로 변환
                        if (hour == 0) 12 else hour
                    }

                    val formattedMinute = if (minute < 10) {
                        "0$minute"
                    } else {
                        minute.toString()
                    }

                    val amPm = if (hour > 12) "오후" else "오전"

                    val formattedTime = "$amPm $formattedHour:$formattedMinute" // 시간과 분 합치기

                    tvTravelScheduleTime.text = formattedTime
                }

                findViewById<AppCompatButton>(R.id.btn_schedule_insert).setOnClickListener { view ->

                    Log.d("ScheduleActivityViewModel", scheduleStatusViewModel.registerResult.value.toString())

                    if (scheduleStatusViewModel.registerResult.value.toString().equals("true")) {

                        bottomSheetTravelScheduleDialog.show()

                        scheduleDayViewModel.selectedItem.observe(this) { selectedItem ->
                            selectedItem?.let {
                                bottomSheetTravelScheduleView.findViewById<TextView>(R.id.tv_day_schedule).text = selectedItem
                            }
                        }

                        bottomSheetTravelScheduleView.findViewById<TextView>(R.id.tv_nickname_auth_schedule).text = nickname + "님! \n" + country + "의 일정을 작성해주세요"

                        bottomSheetTravelScheduleView.findViewById<AppCompatButton>(R.id.btn_schedule_datePicker_insert).setOnClickListener {

                            bottomSheetTravelScheduleDialog.dismiss()

                            if (bottomSheetTravelScheduleView.findViewById<EditText>(R.id.et_travel_schedule_todo).text.toString().isEmpty()) {
                                snackBarFilter.insertTravelScheduleSnackBar(view)
                            } else {
                                val schedule = hashMapOf(
                                    "authUid" to auth.currentUser!!.uid,
                                    "country" to country,
                                    "todo" to bottomSheetTravelScheduleView.findViewById<EditText>(R.id.et_travel_schedule_todo).text.toString(),
                                    "todoDate" to tvTravelScheduleTime.text.toString(),
                                    "todoDay" to bottomSheetTravelScheduleView.findViewById<TextView>(R.id.tv_day_schedule).text.toString(),
                                    "category" to bottomSheetTravelScheduleView.findViewById<PowerSpinnerView>(R.id.powerSpinnerView).text.toString(),
                                    "status" to "진행 중"
                                )

                                db.collection("plans").add(schedule)
                                    .addOnSuccessListener {

                                    }
                                    .addOnFailureListener {

                                    }
                            }
                        }
                    } else {
                        Toast.makeText(this, "Day를 클릭하고 일정을 등록해주세요!", Toast.LENGTH_SHORT).show()
                    }
                }
            }
    }

    private fun calculateDayDifference(startDay: String, endDay: String): Int {
        val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val calStartDay = getInstance()
        val calEndDay = getInstance()
        calStartDay.time = simpleDateFormat.parse(startDay)!!
        calEndDay.time = simpleDateFormat.parse(endDay)!!
        val diffInMillis = calEndDay.timeInMillis - calStartDay.timeInMillis
        return (diffInMillis / (24 * 60 * 60 * 1000)).toInt() + 1
    }
}