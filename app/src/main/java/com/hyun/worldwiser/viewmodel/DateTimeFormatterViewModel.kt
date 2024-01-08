package com.hyun.worldwiser.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class DateTimeFormatterViewModel : ViewModel() {

    val _daysUntilStartDay = MutableLiveData<String>()

    val daysUntilStartDay: LiveData<String> = _daysUntilStartDay

}