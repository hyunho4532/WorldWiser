package com.hyun.worldwiser.ui.travel

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.firestore.FirebaseFirestore
import com.hyun.worldwiser.R
import com.hyun.worldwiser.databinding.ActivityRecommendBinding
import com.hyun.worldwiser.model.TravelRecommend
import com.hyun.worldwiser.viewmodel.VerificationSelectViewModel

class RecommendActivity : AppCompatActivity() {

    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()
    private lateinit var activityRecommendBinding: ActivityRecommendBinding
    private val recommendTravelList: ArrayList<TravelRecommend>  = ArrayList()
    private var imageUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityRecommendBinding = DataBindingUtil.setContentView(this, R.layout.activity_recommend)

        val verificationSelectViewModel: VerificationSelectViewModel = ViewModelProvider(this)[VerificationSelectViewModel::class.java]

        activityRecommendBinding.btnTravelRecommendGalleryInsert.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
            startActivityForResult(intent, 200)
        }

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

    override fun startActivityForResult(intent: Intent, requestCode: Int) {
        super.startActivityForResult(intent, requestCode)

        if (requestCode == 200 && requestCode == Activity.RESULT_OK) {
            val selected
        }
    }
}