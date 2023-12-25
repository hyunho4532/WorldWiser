package com.hyun.worldwiser.util

import android.content.Context
import android.view.animation.AnimationUtils
import android.widget.ImageView
import com.hyun.worldwiser.R

class LoadAnimationFilter {
    fun startAnimate(ivIntroAirplane: ImageView, context: Context, activityIntroAirplaneAlpha: Int) {
        val animation = AnimationUtils.loadAnimation(context, R.anim.activity_intro_airplane_alpha)

        ivIntroAirplane.startAnimation(animation)
    }
}