package com.hyun.worldwiser.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.time.LocalDate

class TravelStatusViewModel : ViewModel() {

    private val _travelStatus = MutableLiveData<String>()

    var travelStatus: LiveData<String>
        get() = _travelStatus
        set(value) {
        }

    fun getTravelStatus(todayDate: LocalDate, startDate: LocalDate, endDate: LocalDate) {

       if (startDate.isAfter(todayDate)) {
            _travelStatus.value = "여행 예정"
        } else if (todayDate.isEqual(startDate) || (todayDate.isAfter(startDate) && todayDate.isBefore(endDate))) {
            _travelStatus.value = "현재 여행 중"
        } else {
            _travelStatus.value = "여행 종료"
        }

        travelStatus = _travelStatus
    }
}