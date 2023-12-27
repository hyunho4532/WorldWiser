package com.hyun.worldwiser.ui.travel

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.hyun.worldwiser.R
import com.hyun.worldwiser.adapter.CountryTravelAdapter
import com.hyun.worldwiser.model.CountryTravel

class InsertActivity : AppCompatActivity() {

    private var countryTravelList = arrayListOf<CountryTravel>()
    val db: FirebaseFirestore = FirebaseFirestore.getInstance()
    val auth: FirebaseAuth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_insert)

        val tvNicknameAuthWhoTravel: TextView = findViewById(R.id.tv_nickname_auth_who_travel)
        val btnTravelCountryInsert: AppCompatButton = findViewById(R.id.btn_travel_country_insert)
        val etCountryTravel: EditText = findViewById(R.id.et_country_travel)

        val bottomSheetView = layoutInflater.inflate(R.layout.dialog_bottom_sheet_travel_country_insert, null)
        val bottomSheetDialog = BottomSheetDialog(this)

        val recyclerView: RecyclerView = bottomSheetView.findViewById(R.id.country_travel_recyclerview)

        bottomSheetDialog.setContentView(bottomSheetView)

        db.collection("verifications").document(auth.currentUser!!.uid).get()
            .addOnSuccessListener { document ->
                val countryFavorite = document["country_favorite"].toString()
                val countryFavoriteItems = countryFavorite.split(", ")

                for (item in countryFavoriteItems) {
                    countryTravelList.add(CountryTravel(item.trim()))
                }
            }
            .addOnFailureListener {

            }

        val countryTravelAdapter = CountryTravelAdapter(this, countryTravelList, bottomSheetDialog) { clickedItem ->
            etCountryTravel.setText(clickedItem)
        }

        recyclerView.adapter = countryTravelAdapter

        val linearLayoutManager = LinearLayoutManager(this)

        recyclerView.layoutManager = linearLayoutManager
        recyclerView.setHasFixedSize(true)

        db.collection("verifications").document(auth.currentUser!!.uid).get()
            .addOnSuccessListener { document ->
                val nickname = document["nickname"].toString()

                tvNicknameAuthWhoTravel.text = nickname + "님의 새로운 여행!"
            }
            .addOnFailureListener {

            }

        btnTravelCountryInsert.setOnClickListener {
            bottomSheetDialog.show()
        }
    }
}