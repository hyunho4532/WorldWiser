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

        val travelRecommendImageUrlFirst = intent.getStringExtra("travelRecommendImageUrlFirst")
        val travelRecommendImageUrlSecond = intent.getStringExtra("travelRecommendImageUrlSecond")

        val images: Array<String?> = arrayOf (
            travelRecommendImageUrlFirst,
            travelRecommendImageUrlSecond,
        )

        Log.d("RecommendDetailActivityFirstImageUrl", travelRecommendImageUrlFirst.toString())
        Log.d("RecommendDetailActivitySecondImageUrl", travelRecommendImageUrlSecond.toString())

        slideViewPager = findViewById(R.id.vp_travel_recommend_imageUrl)

        slideViewPager.offscreenPageLimit = 1
        val travelRecommendDetailImageSlideAdapter = TravelRecommendDetailImageSlideAdapter(this, images)
        slideViewPager.adapter = travelRecommendDetailImageSlideAdapter
    }
}