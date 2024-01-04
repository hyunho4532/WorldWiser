package com.hyun.worldwiser.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class VerificationInsertRepository(private val resultCallback: (Boolean) -> Unit) {

    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    fun insertVerification(verification: HashMap<String, String>) {
        db.collection("verifications").document(auth.currentUser!!.uid).set(verification)
            .addOnSuccessListener {
                resultCallback.invoke(true)
            }
            .addOnFailureListener {
                resultCallback.invoke(false)
            }
    }
}