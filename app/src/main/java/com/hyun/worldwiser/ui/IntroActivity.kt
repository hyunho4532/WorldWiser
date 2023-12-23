package com.hyun.worldwiser.ui

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.AnimationUtils
import androidx.databinding.DataBindingUtil
import com.hyun.worldwiser.R
import com.hyun.worldwiser.databinding.ActivityIntroBinding
import com.hyun.worldwiser.type.AuthType
import com.hyun.worldwiser.util.IntentFilter
import kotlinx.coroutines.*

@DelicateCoroutinesApi
class IntroActivity : AppCompatActivity() {

    private lateinit var activityIntroBinding: ActivityIntroBinding
    private var authType = AuthType.UnAuthStatus
    private var intentFilter: IntentFilter = IntentFilter()

    private lateinit var context: Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityIntroBinding = DataBindingUtil.setContentView(this, R.layout.activity_intro)

        context = applicationContext

        activityIntroBinding.tvIntroStatus.text = "로딩 중"

        GlobalScope.launch {
            delay(2000L)

            withContext(Dispatchers.Main) {
                if (authType == AuthType.UnAuthStatus) {
                    activityIntroBinding.tvIntroStatus.text = "로그인 페이지로 이동 중"
                    delay(1000L)

                    intentFilter.getIntent(context)

                } else {
                    activityIntroBinding.tvIntroStatus.text = "사용자 계정 불러오는 중"
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()

        val animation = AnimationUtils.loadAnimation(this, R.anim.activity_intro_airplane_alpha)
        activityIntroBinding.ivIntroAirplane.startAnimation(animation)
    }
}