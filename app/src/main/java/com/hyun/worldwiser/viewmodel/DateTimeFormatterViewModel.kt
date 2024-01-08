package com.hyun.worldwiser.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

class DateTimeFormatterViewModel : ViewModel() {

    private val _daysUntilStartDay = MutableLiveData<Int>()

    private val _travelStatus = MutableLiveData<String>()
    val travelStatus: LiveData<String> = _travelStatus

    fun settingDateTimeFormatter(formatter: DateTimeFormatter, startDay: String) {
        val currentDate = LocalDate.now()

        val sDay = LocalDate.parse(startDay, formatter)

        val daysUntilStartDay = ChronoUnit.DAYS.between(currentDate, sDay)

        _daysUntilStartDay.value = daysUntilStartDay.toInt()

        _travelStatus.value = when {
            daysUntilStartDay > 0 -> "여행 시작일이 $daysUntilStartDay 일 남았어요!!"
            daysUntilStartDay == 0L -> "여행하고 있어요!"
            else -> "여행이 끝났어요!"
        }
    }
}