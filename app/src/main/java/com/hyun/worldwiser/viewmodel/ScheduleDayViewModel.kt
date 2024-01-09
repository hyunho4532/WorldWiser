package com.hyun.worldwiser.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ScheduleDayViewModel : ViewModel() {
    var selectedDayItem = MutableLiveData<String>()
}