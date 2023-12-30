package com.hyun.worldwiser.ui.schedule

import android.annotation.SuppressLint
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.hyun.worldwiser.R

class ScheduleActivity : AppCompatActivity() {

    private val db = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()
    private lateinit var context: Context

    private lateinit var country: String

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_schedule)

        val travelCountryIntent = intent.getStringExtra("country")

        context = applicationContext

        val bottomSheetTravelScheduleView = layoutInflater.inflate(R.layout.dialog_bottom_sheet_travel_schedule, null)

        val bottomSheetTravelScheduleDialog = BottomSheetDialog(this)

        bottomSheetTravelScheduleDialog.setContentView(bottomSheetTravelScheduleView)

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

        db.collection("verifications").document(auth.currentUser!!.uid).get()
            .addOnSuccessListener { document ->

                val nickname = document["nickname"].toString()

                findViewById<AppCompatButton>(R.id.btn_schedule_insert).setOnClickListener {
                    bottomSheetTravelScheduleDialog.show()

                    bottomSheetTravelScheduleView.findViewById<TextView>(R.id.tv_nickname_auth_schedule).text = nickname + "님, " + country + "의 일정을 \n작성해주세요"
                }
            }
    }
}