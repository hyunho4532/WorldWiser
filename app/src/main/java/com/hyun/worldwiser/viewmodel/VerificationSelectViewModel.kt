package com.hyun.worldwiser.viewmodel

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.hyun.worldwiser.model.CountryTravel

class VerificationSelectViewModel : ViewModel() {

    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    fun verificationCountryFavoriteSelectData(countryTravelList: ArrayList<CountryTravel>) {
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
    }

    fun verificationNicknameSelectData(itemClickListener: (String) -> Unit) {
        db.collection("verifications").document(auth.currentUser!!.uid).get()
            .addOnSuccessListener { document ->
                val nickname = document["nickname"].toString()

                itemClickListener.invoke(nickname)
            }
            .addOnFailureListener {

            }
    }
}