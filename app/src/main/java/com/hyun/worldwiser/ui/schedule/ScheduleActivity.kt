package com.hyun.worldwiser.ui.schedule

import android.annotation.SuppressLint
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.DatePicker
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.hyun.worldwiser.R
import com.hyun.worldwiser.adapter.ScheduleAdapter
import com.hyun.worldwiser.adapter.TravelAdapter
import com.hyun.worldwiser.decorator.DayDecorator
import com.hyun.worldwiser.decorator.SaturdayDecorator
import com.hyun.worldwiser.decorator.SundayDecorator
import com.hyun.worldwiser.model.Schedule
import com.hyun.worldwiser.util.SnackBarFilter
import com.prolificinteractive.materialcalendarview.MaterialCalendarView
import java.lang.NullPointerException
import java.util.*
import java.util.Calendar.getInstance
import kotlin.collections.ArrayList

class ScheduleActivity : AppCompatActivity() {

    private val db = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()
    private lateinit var context: Context

    private var scheduleList: ArrayList<Schedule> = ArrayList()

    private val snackBarFilter: SnackBarFilter = SnackBarFilter()

    private lateinit var country: String

    @SuppressLint("SetTextI18n", "NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_schedule)

        context = applicationContext

        val dayDecorator = DayDecorator(context)
        val sunDayDecorator = SundayDecorator()
        val saturdayDecorator = SaturdayDecorator()

        val travelCountryIntent = intent.getStringExtra("country")

        val bottomSheetTravelScheduleView = layoutInflater.inflate(R.layout.dialog_bottom_sheet_travel_schedule, null)
        val bottomSheetTravelScheduleDatePickerView = layoutInflater.inflate(R.layout.dialog_bottom_sheet_travel_schedule_datepicker, null)

        val bottomSheetTravelScheduleDialog = BottomSheetDialog(this)
        val bottomSheetTravelScheduleDatePickerDialog = BottomSheetDialog(this)

        bottomSheetTravelScheduleDialog.setContentView(bottomSheetTravelScheduleView)
        bottomSheetTravelScheduleDatePickerDialog.setContentView(bottomSheetTravelScheduleDatePickerView)

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
                }

            }

        db.collection("plans").whereEqualTo("authUid", auth.currentUser!!.uid).whereEqualTo("country", travelCountryIntent).get()
            .addOnSuccessListener { querySnapshot ->

                for (document in querySnapshot.documents) {
                    scheduleList.add(Schedule(document["todo"].toString()))

                    val recyclerView: RecyclerView = bottomSheetTravelScheduleView.findViewById(R.id.recyclerView)

                    val scheduleAdapter = ScheduleAdapter(context, scheduleList)

                    recyclerView.adapter = scheduleAdapter
                    recyclerView.layoutManager = LinearLayoutManager(this)

                    scheduleAdapter.notifyDataSetChanged()
                }

            }

        db.collection("verifications").document(auth.currentUser!!.uid).get()
            .addOnSuccessListener { document ->

                val nickname = document["nickname"].toString()

                findViewById<AppCompatButton>(R.id.btn_schedule_insert).setOnClickListener { view ->

                    bottomSheetTravelScheduleDialog.show()

                    bottomSheetTravelScheduleView.findViewById<TextView>(R.id.tv_nickname_auth_schedule).text = nickname + "님! \n" + country + "의 일정을 작성해주세요"
                    bottomSheetTravelScheduleView.findViewById<AppCompatButton>(R.id.btn_schedule_datePicker_insert).setOnClickListener {

                        if (bottomSheetTravelScheduleView.findViewById<EditText>(R.id.et_travel_schedule_todo).text.toString().isEmpty()) {
                            snackBarFilter.insertTravelScheduleSnackBar(view)
                        } else {
                            val schedule = hashMapOf(
                                "authUid" to auth.currentUser!!.uid,
                                "country" to country,
                                "todo" to bottomSheetTravelScheduleView.findViewById<EditText>(R.id.et_travel_schedule_todo).text.toString()
                            )

                            db.collection("plans").add(schedule)
                                .addOnSuccessListener {

                                }
                                .addOnFailureListener {

                                }
                        }
                    }
                }
            }
    }
}