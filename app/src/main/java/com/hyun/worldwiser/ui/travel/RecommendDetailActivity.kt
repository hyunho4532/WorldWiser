package com.hyun.worldwiser.ui.travel

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.viewpager2.widget.ViewPager2
import com.hyun.worldwiser.R
import com.hyun.worldwiser.adapter.TravelRecommendDetailImageSlideAdapter

class RecommendDetailActivity : AppCompatActivity() {

    private lateinit var slideViewPager: ViewPager2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recommend_detail)

        val travelRecommendImageUrl = intent.getStringExtra("travelRecommendImageUrl")

        Log.d("travelRecommendImageUrl", travelRecommendImageUrl.toString())

        val travelRecommendImages = arrayOf (
            travelRecommendImageUrl
        )


        slideViewPager = findViewById(R.id.vp_travel_recommend_imageUrl)

        slideViewPager.offscreenPageLimit = 1
        val travelRecommendDetailImageSlideAdapter = TravelRecommendDetailImageSlideAdapter(this, travelRecommendImages)
        slideViewPager.adapter = travelRecommendDetailImageSlideAdapter
    }
}