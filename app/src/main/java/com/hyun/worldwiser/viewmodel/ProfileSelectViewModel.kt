package com.hyun.worldwiser.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class ProfileSelectViewModel : ViewModel()  {

    val country: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    private val imageUrl: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }


    private val startDay: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }


    private val endDay: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    private val db : FirebaseFirestore = FirebaseFirestore.getInstance()
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    fun getTravelInserts() {
        db.collection("travelInserts").whereEqualTo("authUid", auth.currentUser!!.uid).get()
            .addOnSuccessListener { querySnapshot  ->

                for (document in querySnapshot.documents) {
                    country.postValue(document["country"].toString())
                    imageUrl.postValue(document["imageUrl"].toString())
                    startDay.postValue(document["startDay"].toString())
                    endDay.postValue(document["endDay"].toString())
                }
            }
            .addOnFailureListener {

            }
    }

}