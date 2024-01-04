package com.hyun.worldwiser.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class VerificationInsertViewModel(private val resultCallback: (Boolean) -> Unit) : ViewModel() {


    private val _verificationResults = MutableLiveData<Boolean>()
    val verificationResults: LiveData<Boolean> = _verificationResults
}