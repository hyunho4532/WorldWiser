package com.hyun.worldwiser.ui.schedule

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.hyun.worldwiser.R

class ScheduleActivity : AppCompatActivity() {

    private val db = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()
    private lateinit var context: Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_schedule)

        val travelCountryIntent = intent.getStringExtra("country")

        context = applicationContext

        db.collection("travelInserts").whereEqualTo("authUid", auth.currentUser!!.uid).whereEqualTo("country", travelCountryIntent).get()
            .addOnSuccessListener { querySnapshot ->

                for (document in querySnapshot.documents) {
                    val imageUrl = document["imageUrl"].toString()
                    val country = document["country"].toString()
                    val startDay = document["startDay"].toString()
                    val endDay = document["endDay"].toString()

                    findViewById<TextView>(R.id.tv_travel_schedule_country).text = country
                    findViewById<TextView>(R.id.tv_travel_schedule_calendar).text = startDay + " ~ " + endDay

                    Glide.with(context)
                        .load(imageUrl)
                        .into(findViewById(R.id.iv_travel_schedule_url))
                }

            }
    }
}