package com.hyun.worldwiser.repository

import com.google.firebase.auth.FirebaseAuth

class AuthLoginRepository(private val resultCallback: (Boolean) -> Unit) {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    fun signInWithEmailAndPassword(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                resultCallback.invoke(true)
            }
            .addOnFailureListener {
                resultCallback.invoke(false)
            }
    }
}