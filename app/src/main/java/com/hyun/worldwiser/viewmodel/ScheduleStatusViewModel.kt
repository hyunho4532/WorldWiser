package com.hyun.worldwiser.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ScheduleStatusViewModel : ViewModel() {
    private val _scheduleStatusResult = MutableLiveData<Boolean>().apply {
        value = false
    }

    val registerResult: LiveData<Boolean> = _scheduleStatusResult

    fun registerStatus(scheduleStatus: Boolean) {
        _scheduleStatusResult.postValue(scheduleStatus)
    }
}