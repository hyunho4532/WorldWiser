package com.hyun.worldwiser.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.AnimationUtils
import android.widget.ImageView
import com.hyun.worldwiser.R

class IntroActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro)
    }

    override fun onResume() {
        super.onResume()

        val ivIntroAirplane = findViewById<ImageView>(R.id.iv_intro_airplane)
        val animation = AnimationUtils.loadAnimation(this, R.anim.activtiy_intro_airplane_alpha)

        ivIntroAirplane.startAnimation(animation)
    }
}