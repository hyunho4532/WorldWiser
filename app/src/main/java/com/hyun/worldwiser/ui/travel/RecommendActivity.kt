package com.hyun.worldwiser.ui.travel

import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.hyun.worldwiser.Manifest
import com.hyun.worldwiser.R
import com.hyun.worldwiser.databinding.ActivityRecommendBinding
import com.hyun.worldwiser.model.TravelRecommend
import com.hyun.worldwiser.viewmodel.VerificationSelectViewModel

class RecommendActivity : AppCompatActivity() {

    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private lateinit var activityRecommendBinding: ActivityRecommendBinding
    private val recommendTravelList: ArrayList<TravelRecommend>  = ArrayList()
    private lateinit var imageView: ImageView
    private var imageUri: Uri? = null

    private val requestPermissionLauncher: ActivityResultLauncher<String> =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted)
                openGallery()
        }

    private val pickImageLauncher: ActivityResultLauncher<Intent> =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val data: Intent? = result.data
                data?.data?.let {
                    imageUri = it
                    imageView.setImageURI(imageUri)
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityRecommendBinding = DataBindingUtil.setContentView(this, R.layout.activity_recommend)

        val verificationSelectViewModel: VerificationSelectViewModel = ViewModelProvider(this)[VerificationSelectViewModel::class.java]

        activityRecommendBinding.ivTravelRecommendGallery.clipToOutline = true

        activityRecommendBinding.ivTravelRecommendGallery.setOnClickListener {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                openGallery()
            } else {
                requestPermissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
            }
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
}