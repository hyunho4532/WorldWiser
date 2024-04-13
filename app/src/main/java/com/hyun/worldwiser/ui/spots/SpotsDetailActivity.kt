package com.hyun.worldwiser.ui.spots

import android.os.Bundle
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.hyun.worldwiser.R
import com.hyun.worldwiser.databinding.ActivitySpotsDetailBinding

class SpotsDetailActivity : AppCompatActivity() {

    private lateinit var activitySpotsDetailBinding: ActivitySpotsDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_spots_detail)

    }
}