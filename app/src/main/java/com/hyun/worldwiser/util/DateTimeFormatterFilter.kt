package com.hyun.worldwiser.util

import com.hyun.worldwiser.databinding.FragmentProfileBinding
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

class DateTimeFormatterFilter {

    fun settingDateTimeFormatter(formatter: DateTimeFormatter, startDay: String, endDay: String, fragmentProfileBinding: FragmentProfileBinding) {
        val currentDate = LocalDate.now()

        val sDay = LocalDate.parse(startDay, formatter)
        val eDay = LocalDate.parse(endDay, formatter)

        val daysUntilStartDay = ChronoUnit.DAYS.between(currentDate, sDay)
        val daysUntilEndDay = ChronoUnit.DAYS.between(currentDate, eDay)

        if (daysUntilStartDay > 0) {
            fragmentProfileBinding.tvTravelCalendarDay.text = "여행 시작일이 " + daysUntilStartDay.toString() + "일 남았어요!!"
        } else if (daysUntilStartDay == 0L) {
            fragmentProfileBinding.tvTravelCalendarDay.text = "여행하고 있어요!"
        } else if (daysUntilEndDay < 0) {
            fragmentProfileBinding.tvTravelCalendarDay.text = "여행이 끝났어요!"
        }
    }
}