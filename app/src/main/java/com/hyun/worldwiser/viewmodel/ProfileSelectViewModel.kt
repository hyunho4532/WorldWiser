package com.hyun.worldwiser.viewmodel

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class ProfileSelectViewModel(
    val country: (String) -> Unit,
    val imageUrl: (String) -> Unit,
    val startDay: (String) -> Unit,
    val endDay: (String) -> Unit
) {

    private val db : FirebaseFirestore = FirebaseFirestore.getInstance()
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    fun

}