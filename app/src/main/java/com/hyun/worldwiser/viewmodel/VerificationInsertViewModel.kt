package com.hyun.worldwiser.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hyun.worldwiser.repository.VerificationInsertRepository

class VerificationInsertViewModel() : ViewModel() {

    private val verificationInsertRepository: VerificationInsertRepository = VerificationInsertRepository { success ->
        _verificationResults.postValue(success)
    }

    private val _verificationResults = MutableLiveData<Boolean>()
    val verificationResults: LiveData<Boolean> = _verificationResults

    fun insertVerification(verification: HashMap<String, String>) {
        verificationInsertRepository.insertVerification(verification)
    }
}