package com.hyun.worldwiser.ui.travel

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.hyun.worldwiser.R
import com.hyun.worldwiser.adapter.CountryTravelAdapter
import com.hyun.worldwiser.databinding.ActivityInsertBinding
import com.hyun.worldwiser.decorator.CalendarDecorator
import com.hyun.worldwiser.model.CountryTravel
import com.hyun.worldwiser.viewmodel.VerificationSelectViewModel
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.MaterialCalendarView

class InsertActivity : AppCompatActivity() {

    private var countryTravelList = arrayListOf<CountryTravel>()

    private val verificationSelectViewModel: VerificationSelectViewModel = VerificationSelectViewModel()
    private val calendarDecorator: CalendarDecorator = CalendarDecorator()

    private lateinit var activityInsertBinding: ActivityInsertBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityInsertBinding = DataBindingUtil.setContentView(this, R.layout.activity_insert)

        val bottomSheetTravelCountryView = layoutInflater.inflate(R.layout.dialog_bottom_sheet_travel_country_insert, null)
        val bottomSheetTravelCalendarView = layoutInflater.inflate(R.layout.dialog_bottom_sheet_travel_calendar_insert, null)

        val bottomSheetTravelCountryDialog = BottomSheetDialog(this)
        val bottomSheetTravelCalendarDialog = BottomSheetDialog(this)

        val recyclerView: RecyclerView = bottomSheetTravelCountryView.findViewById(R.id.country_travel_recyclerview)
        val materialCalendarView: MaterialCalendarView = bottomSheetTravelCalendarView.findViewById(R.id.calendar)

        bottomSheetTravelCountryDialog.setContentView(bottomSheetTravelCountryView)
        bottomSheetTravelCalendarDialog.setContentView(bottomSheetTravelCalendarView)

        verificationSelectViewModel.verificationCountryFavoriteSelectData(countryTravelList)

        recyclerView.adapter = CountryTravelAdapter(this, countryTravelList, bottomSheetTravelCountryDialog) { clickedItem ->
            activityInsertBinding.etCountryTravel.setText(clickedItem)
        }

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)

        verificationSelectViewModel.verificationNicknameSelectData { nickname ->
            activityInsertBinding.tvNicknameAuthWhoTravel.setText(nickname + "님의 새로운 여행!")
        }

        activityInsertBinding.btnTravelCountryInsert.setOnClickListener {
            bottomSheetTravelCountryDialog.show()
        }

        activityInsertBinding.btnTravelCalendarInsert.setOnClickListener {
            bottomSheetTravelCalendarDialog.show()

            materialCalendarView.selectedDate = CalendarDay.today()
            materialCalendarView.addDecorator(calendarDecorator)
        }
    }
}