package com.hyun.worldwiser.util

import android.app.Activity
import android.content.Context
import android.content.Intent

class IntentFilter {
    fun getIntent(context: Context, nextActivity: Activity) {
        val intent = Intent(context, nextActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        context.startActivity(intent)
    }
}