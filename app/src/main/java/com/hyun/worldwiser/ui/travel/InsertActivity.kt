package com.hyun.worldwiser.ui.travel

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.hyun.worldwiser.R

class InsertActivity : AppCompatActivity() {

    val db: FirebaseFirestore = FirebaseFirestore.getInstance()
    val auth: FirebaseAuth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_insert)

        val bottomSheetView = layoutInflater.inflate(R.layout.dialog_bottom_sheet_travel_country_insert, null)
        val bottomSheetDialog = BottomSheetDialog(this)
        bottomSheetDialog.setContentView(bottomSheetView)

        val tvNicknameAuthWhoTravel: TextView = findViewById(R.id.tv_nickname_auth_who_travel)
        val btnTravelCountryInsert: AppCompatButton = findViewById(R.id.btn_travel_country_insert)

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