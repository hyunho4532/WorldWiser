package com.hyun.worldwiser.ui.travel

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.hyun.worldwiser.R

class InsertActivity : AppCompatActivity() {

    val db: FirebaseFirestore = FirebaseFirestore.getInstance()
    val auth: FirebaseAuth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_insert)

        val tvNicknameAuthWhoTravel: TextView = findViewById(R.id.tv_nickname_auth_who_travel)

        db.collection("verifications").document(auth.currentUser!!.uid).get()
            .addOnSuccessListener { document ->
                val nickname = document["nickname"].toString()

                tvNicknameAuthWhoTravel.text = nickname + "님의 새로운 여행!"
            }
            .addOnFailureListener {

            }
    }
}