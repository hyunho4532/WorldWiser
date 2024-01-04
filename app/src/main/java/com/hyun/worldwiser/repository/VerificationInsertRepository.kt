package com.hyun.worldwiser.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class VerificationInsertRepository(private val resultCallback: (Boolean) -> Unit) {

    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    fun insertVerification() {
        db.collection("verifications").document(auth.currentUser!!.uid).set(verification)
            .addOnSuccessListener {
                resultCallb
            }
            .addOnFailureListener {
                snackBarFilter.getVerificationFailureSnackBar(view)
            }
    }
}