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
import com.hyun.worldwiser.decorator.TodayDecorator
import com.hyun.worldwiser.model.CountryTravel
import com.hyun.worldwiser.model.UnsplashPhoto
import com.hyun.worldwiser.service.UnsplashApiService
import com.hyun.worldwiser.util.SnackBarFilter
import com.hyun.worldwiser.viewmodel.VerificationSelectViewModel
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.MaterialCalendarView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class InsertActivity : AppCompatActivity() {

    private var countryTravelList = arrayListOf<CountryTravel>()

    private val verificationSelectViewModel: VerificationSelectViewModel = VerificationSelectViewModel()

    private lateinit var context: Context

    private lateinit var startDay: String
    private val snackBarFilter: SnackBarFilter = SnackBarFilter()

    private lateinit var activityInsertBinding: ActivityInsertBinding

    val db = FirebaseFirestore.getInstance()
    val auth = FirebaseAuth.getInstance()

    private lateinit var imageAdapter: ImageAdapter
    private val imageUrls = mutableListOf<String>()
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
        call.enqueue(object : Callback<List<UnsplashPhoto>> {
            @SuppressLint("NotifyDataSetChanged")
            override fun onResponse(
                call: Call<List<UnsplashPhoto>>,
                response: Response<List<UnsplashPhoto>>
            ) {
                if (!response.isSuccessful) {
                    return
                }

                val photos = response.body()
                photos?.forEach { photo ->
                    imageUrls.add(photo.urls.regular)
                }

                imageAdapter = ImageAdapter(imageUrls) { imageUrl ->
                    activityInsertBinding.tvSelectTravelTheme.text = imageUrl

                    val travelInsert = hashMapOf(
                        "imageUrl" to activityInsertBinding.tvSelectTravelTheme.text
                    )

                    activityInsertBinding.btnTravelInsert.setOnClickListener { view ->
                        db.collection("travelInserts").document(auth.currentUser!!.uid).set(travelInsert)
                            .addOnSuccessListener {
                                snackBarFilter.getEmailInsertSnackBar(view)
                            }
                    }

                    imageAdapter.notifyDataSetChanged()
                }

                activityInsertBinding.rvTravelTheme.adapter = imageAdapter
            }

            override fun onFailure(call: Call<List<UnsplashPhoto>>, t: Throwable) {

            }
        })

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