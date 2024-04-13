package com.hyun.worldwiser.ui.trend

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import com.hyun.worldwiser.R

class TrendAnalysisActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_trend_analysis)

        val viewPager = findViewById<ViewPager2>(R.id.trendAnalysisViewPager)
        val adapter = Trend
    }
}