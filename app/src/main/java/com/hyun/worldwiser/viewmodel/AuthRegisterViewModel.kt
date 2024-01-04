package com.hyun.worldwiser.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hyun.worldwiser.repository.AuthRegisterRepository

class AuthRegisterViewModel : ViewModel() {

    private val authRegisterRepository = AuthRegisterRepository() { isSuccess ->
        _loginResult.postValue(isSuccess)
    }

    private val _loginResult = MutableLiveData<Boolean>()
    val loginResult: LiveData<Boolean> = _loginResult

    fun registerUsers(email: String, password: String) {
        authRegisterRepository.createUserWithEmailAndPassword(email, password)
    }
}