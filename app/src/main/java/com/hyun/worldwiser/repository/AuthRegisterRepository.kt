package com.hyun.worldwiser.repository

import com.google.firebase.auth.FirebaseAuth

class AuthRegisterRepository(private val resultCallback: (Boolean) -> Unit) {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    fun createUserWithEmailAndPassword(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                resultCallback.invoke(true)
            }
            .addOnFailureListener {
                resultCallback.invoke(false)
            }
    }
}