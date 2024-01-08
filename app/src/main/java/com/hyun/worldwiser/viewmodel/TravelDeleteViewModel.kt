package com.hyun.worldwiser.viewmodel

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class TravelDeleteViewModel : ViewModel() {

    private val firebaseFirestore: FirebaseFirestore = FirebaseFirestore.getInstance()
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    fun travelDeleteViewModel(country: String, resultCallback: (Boolean) -> Unit) {
        val deleteQuery = firebaseFirestore.collection("travelInserts").whereEqualTo("country", country).whereEqualTo("authUid", auth.currentUser!!.uid)

        deleteQuery.get().addOnSuccessListener { documents ->
            for (document in documents) {
                firebaseFirestore.collection("travelInserts").document(document.id).delete()
                    .addOnSuccessListener {
                        resultCallback.invoke(true)
                    }
                    .addOnFailureListener {
                        resultCallback.invoke(false)
                    }
            }
        }
    }
}