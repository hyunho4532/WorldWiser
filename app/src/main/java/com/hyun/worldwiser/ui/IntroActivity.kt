package com.hyun.worldwiser.ui

import android.app.Activity
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.AnimationUtils
import androidx.databinding.DataBindingUtil
import com.google.firebase.auth.FirebaseAuth
import com.hyun.worldwiser.R
import com.hyun.worldwiser.databinding.ActivityIntroBinding
import com.hyun.worldwiser.type.AuthType
import com.hyun.worldwiser.ui.register.RegisterActivity
import com.hyun.worldwiser.util.IntentFilter
import kotlinx.coroutines.*

@DelicateCoroutinesApi
class IntroActivity : AppCompatActivity() {

    private lateinit var activityIntroBinding: ActivityIntroBinding
    private var authType = AuthType.UnAuthStatus
    private var intentFilter: IntentFilter = IntentFilter()

    private val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()

    private lateinit var startContext: Context

    private val registerActivity: RegisterActivity = RegisterActivity()
    private val mainActivity: MainActivity = MainActivity()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityIntroBinding = DataBindingUtil.setContentView(this, R.layout.activity_intro)

        startContext = applicationContext

        activityIntroBinding.tvIntroStatus.text = "로딩 중"

        GlobalScope.launch {
            delay(2000L)

            withContext(Dispatchers.Main) {
                val currentUser = firebaseAuth.currentUser

                if (currentUser == null) {
                    moveToLogin()
                } else {
                    loadingToAccount()
                }
            }
        }
    }

    private fun moveToLogin() {
        activityIntroBinding.tvIntroStatus.text = "로그인 페이지로 이동 중"
        delayAndMoveToActivity(registerActivity)
    }

    private suspend fun loadingToAccount() {
        activityIntroBinding.tvIntroStatus.text = "사용자 계정 불러오는 중"
        delay(1000L)

        moveToMain()
    }

    private fun moveToMain() {
        activityIntroBinding.tvIntroStatus.text = "메인 페이지로 이동 중"
        delayAndMoveToActivity(mainActivity)
    }

    private fun delayAndMoveToActivity(activity: Activity) {
        GlobalScope.launch {
            delay(1000L)
            intentFilter.getIntent(startContext, activity)
            finish()
        }
    }

    override fun onResume() {
        super.onResume()

        val animation = AnimationUtils.loadAnimation(this, R.anim.activity_intro_airplane_alpha)
        activityIntroBinding.ivIntroAirplane.startAnimation(animation)
    }
}