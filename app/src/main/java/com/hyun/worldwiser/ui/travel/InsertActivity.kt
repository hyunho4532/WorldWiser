package com.hyun.worldwiser.ui.travel

import android.annotation.SuppressLint
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.hyun.worldwiser.R
import com.hyun.worldwiser.adapter.CountryTravelAdapter
import com.hyun.worldwiser.adapter.ImageAdapter
import com.hyun.worldwiser.databinding.ActivityInsertBinding
import com.hyun.worldwiser.decorator.DayDecorator
import com.hyun.worldwiser.decorator.SaturdayDecorator
import com.hyun.worldwiser.decorator.SundayDecorator
import com.hyun.worldwiser.model.CountryTravel
import com.hyun.worldwiser.model.UnsplashPhoto
import com.hyun.worldwiser.service.UnsplashApiService
import com.hyun.worldwiser.viewmodel.UnsplashApiInsertViewModel
import com.hyun.worldwiser.viewmodel.VerificationInsertViewModel
import com.hyun.worldwiser.viewmodel.VerificationSelectViewModel
import com.prolificinteractive.materialcalendarview.MaterialCalendarView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class InsertActivity : AppCompatActivity() {

    private var countryTravelList = arrayListOf<CountryTravel>()

    private val unsplashApiInsertViewModel: UnsplashApiInsertViewModel = UnsplashApiInsertViewModel()
    private val verificationSelectViewModel: VerificationSelectViewModel = VerificationSelectViewModel()
    private val verificationInsertViewModel: VerificationInsertViewModel = VerificationInsertViewModel()

    private lateinit var context: Context

    private lateinit var activityInsertBinding: ActivityInsertBinding

    val db = FirebaseFirestore.getInstance()
    val auth = FirebaseAuth.getInstance()

    private lateinit var imageAdapter: ImageAdapter
    private val imageUrls = mutableListOf<String>()
    private lateinit var imageGetUrl: String

    private val UNSPLASH_ACCESS_KEY = "PoIXl8gzJfzjf6uBVST58qmwx74fCQdE-gvu8fnE9uQ"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityInsertBinding = DataBindingUtil.setContentView(this, R.layout.activity_insert)

        context = applicationContext

        val dayDecorator = DayDecorator(context)
        val sunDayDecorator = SundayDecorator()
        val saturdayDecorator = SaturdayDecorator()

        val bottomSheetTravelCountryView = layoutInflater.inflate(R.layout.dialog_bottom_sheet_travel_country_insert, null)
        val bottomSheetTravelCalendarView = layoutInflater.inflate(R.layout.dialog_bottom_sheet_travel_calendar_insert, null)

        val bottomSheetTravelCountryDialog = BottomSheetDialog(this)
        val bottomSheetTravelCalendarDialog = BottomSheetDialog(this)

        val recyclerView: RecyclerView = bottomSheetTravelCountryView.findViewById(R.id.country_travel_recyclerview)
        val materialCalendarView: MaterialCalendarView = bottomSheetTravelCalendarView.findViewById(R.id.calendar_view)

        activityInsertBinding.rvTravelTheme.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.unsplash.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(UnsplashApiService::class.java)

        val call = service.getRandomPhotos(UNSPLASH_ACCESS_KEY, 10)

        activityInsertBinding.switcher.setOnCheckedChangedListener { checked ->
            if (checked) {
                activityInsertBinding.tvTravelStatus.text = "혼자 여행"
            } else {
                activityInsertBinding.tvTravelStatus.text = "동행 여행"
            }
        }

        unsplashApiInsertViewModel.loadUnsplashAPIEnqueue(call, activityInsertBinding, imageUrls) { imageAdapter ->
            activityInsertBinding.rvTravelTheme.adapter = imageAdapter

            imageAdapter.notifyDataSetChanged()
        }

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

        materialCalendarView.setHeaderTextAppearance(R.style.CalendarWidgetHeader)

        activityInsertBinding.btnTravelCalendarInsert.setOnClickListener {
            bottomSheetTravelCalendarDialog.show()

            materialCalendarView.addDecorators(dayDecorator, sunDayDecorator, saturdayDecorator)
        }

        materialCalendarView.setOnRangeSelectedListener { _, dates ->
            bottomSheetTravelCalendarDialog.dismiss()

            activityInsertBinding.etTravelCalendarStart.setText(dates[0].date.toString())
            activityInsertBinding.etTravelCalendarEnd.setText(dates[dates.size - 1].date.toString())
        }
    }
}