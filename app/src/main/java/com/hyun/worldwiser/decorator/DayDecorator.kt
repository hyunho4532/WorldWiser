package com.hyun.worldwiser.decorator

import android.content.Context
import androidx.core.content.ContextCompat
import com.hyun.worldwiser.R
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.DayViewDecorator
import com.prolificinteractive.materialcalendarview.DayViewFacade

class DayDecorator(context: Context) : DayViewDecorator {

    private val drawable = ContextCompat.getDrawable(context, R.drawable.calendar_selector)

    override fun shouldDecorate(day: CalendarDay): Boolean {
        return true
    }

    override fun decorate(view: DayViewFacade) {
        view.setSelectionDrawable(drawable!!)
    }
}