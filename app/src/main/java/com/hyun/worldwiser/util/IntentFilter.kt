package com.hyun.worldwiser.util

import android.content.Context
import android.content.Intent
import com.hyun.worldwiser.ui.IntroActivity
import com.hyun.worldwiser.ui.MainActivity
import kotlinx.coroutines.DelicateCoroutinesApi

class IntentFilter {
    fun getIntent(context: Context) {
        val intent = Intent(context, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        context.startActivity(intent)
    }
}