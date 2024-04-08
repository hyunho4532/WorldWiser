package com.hyun.worldwiser.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hyun.worldwiser.repository.AuthLoginRepository
import com.hyun.worldwiser.repository.AuthRegisterRepository

class AuthLoginViewModel : ViewModel() {

    private val authLoginRepository = AuthLoginRepository() { isSuccess ->
        _loginResult.postValue(isSuccess)
    }

    private val _loginResult = MutableLiveData<Boolean>()
    val loginResult: LiveData<Boolean> = _loginResult

    fun loginUsers(email: String, password: String) {
        authLoginRepository.signInWithEmailAndPassword(email, password)
    }
}