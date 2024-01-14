package com.hyun.worldwiser.ui.travel

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.hyun.worldwiser.R
import com.hyun.worldwiser.databinding.ActivityRecommendBinding
import com.hyun.worldwiser.model.TravelRecommend
import com.hyun.worldwiser.viewmodel.VerificationSelectViewModel

class RecommendActivity : AppCompatActivity() {

    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private lateinit var activityRecommendBinding: ActivityRecommendBinding
    private val recommendTravelList: ArrayList<TravelRecommend>  = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityRecommendBinding = DataBindingUtil.setContentView(this, R.layout.activity_recommend)

        val verificationSelectViewModel: VerificationSelectViewModel = ViewModelProvider(this)[VerificationSelectViewModel::class.java]

        verificationSelectViewModel.verificationNicknameSelectData { nickname ->
            activityRecommendBinding.tvNicknameAuthWhoTravelRecommend.setText(nickname + "님의 추천 여행지는?")
        }

        activityRecommendBinding.switcher.setOnCheckedChangedListener { checked ->
            if (checked) {
                activityRecommendBinding.tvTravelWhereTravel.visibility = View.VISIBLE
                activityRecommendBinding.etTravelFinishCountry.visibility = View.VISIBLE
                activityRecommendBinding.tvTravelRecommendWhereTravel.visibility = View.INVISIBLE
                activityRecommendBinding.etTravelRecommendCountry.visibility = View.INVISIBLE
            } else {
                activityRecommendBinding.tvTravelRecommendWhereTravel.visibility = View.VISIBLE
                activityRecommendBinding.etTravelRecommendCountry.visibility = View.VISIBLE
                activityRecommendBinding.tvTravelWhereTravel.visibility = View.INVISIBLE
                activityRecommendBinding.etTravelFinishCountry.visibility = View.INVISIBLE
            }
        }

        activityRecommendBinding.switcherTravelRecommendStatusAlone.setOnCheckedChangedListener { checked ->
            if (checked) {
                activityRecommendBinding.tvTravelRecommendAloneStatus.text = "혼자 여행"
            } else {
                activityRecommendBinding.tvTravelRecommendAloneStatus.text = "동행 여행"
            }
        }

        activityRecommendBinding.btnTravelRecommendInsert.setOnClickListener {

            val travelRecommendCountry = activityRecommendBinding.etTravelRecommendCountry.text.toString()
            val travelRecommendImpression = activityRecommendBinding.etTravelRecommendImpression.text.toString()
            val travelRecommendAloneStatus = activityRecommendBinding.tvTravelRecommendAloneStatus.text.toString()

            val travelRecommend = hashMapOf (
                "travelRecommendCountry" to travelRecommendCountry,
                "travelRecommendImpression" to travelRecommendImpression,
                "travelRecommendAloneStatus" to travelRecommendAloneStatus,
            )

            recommendTravelList.add (
                TravelRecommend(travelRecommendCountry, travelRecommendAloneStatus, travelRecommendImpression)
            )

            db.collection("travelRecommends").add(travelRecommend)
                .addOnSuccessListener {

                }
        }
    }
}