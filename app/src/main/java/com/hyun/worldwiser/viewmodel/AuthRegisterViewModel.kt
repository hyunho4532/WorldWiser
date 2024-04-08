package com.hyun.worldwiser.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hyun.worldwiser.repository.AuthRegisterRepository

class AuthRegisterViewModel : ViewModel() {

    private val authRegisterRepository = AuthRegisterRepository() { isSuccess ->
        _registerResult.postValue(isSuccess)
    }

    private val _registerResult = MutableLiveData<Boolean>()
    val registerResult: LiveData<Boolean> = _registerResult

    fun registerUsers(email: String, password: String) {
        authRegisterRepository.createUserWithEmailAndPassword(email, password)
    }
}