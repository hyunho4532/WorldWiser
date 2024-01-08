package com.hyun.worldwiser.util

import android.content.Context
import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.hyun.worldwiser.databinding.FragmentProfileBinding
import com.hyun.worldwiser.viewmodel.DateTimeFormatterViewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

class DateTimeFormatterFilter {

    fun settingDateTimeFormatter(formatter: DateTimeFormatter, startDay: String): String {
        val currentDate = LocalDate.now()

        val sDay = LocalDate.parse(startDay, formatter)

        val daysUntilStartDay = ChronoUnit.DAYS.between(currentDate, sDay)

        Log.d("DateTimeFormatter", daysUntilStartDay.toString())

        val dateTimeFormatterViewModel = ViewModelProvider()[DateTimeFormatterViewModel::class.java]
        dateTimeFormatterViewModel._daysUntilStartDay.postValue(daysUntilStartDay.toString())

        return when {
            daysUntilStartDay > 0 -> "여행 시작일이 $daysUntilStartDay 일 남았어요!!"
            daysUntilStartDay == 0L -> "여행하고 있어요!"
            else -> "여행이 끝났어요!"
        }

    }
}