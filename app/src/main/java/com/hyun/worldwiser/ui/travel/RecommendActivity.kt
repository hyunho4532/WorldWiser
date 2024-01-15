package com.hyun.worldwiser.ui.travel

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.firestore.FirebaseFirestore
import com.hyun.worldwiser.FirebaseStorageManager
import com.hyun.worldwiser.R
import com.hyun.worldwiser.databinding.ActivityRecommendBinding
import com.hyun.worldwiser.model.TravelRecommend
import com.hyun.worldwiser.type.SelectedImageType
import com.hyun.worldwiser.viewmodel.VerificationSelectViewModel

class RecommendActivity : AppCompatActivity() {

    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()
    private lateinit var activityRecommendBinding: ActivityRecommendBinding
    private val recommendTravelList: ArrayList<TravelRecommend>  = ArrayList()

    private var imageUri: Uri? = null
    private lateinit var selectedImageType: SelectedImageType
    private var bitmap: Bitmap? = null

    private val firebaseStorageManager = FirebaseStorageManager()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityRecommendBinding = DataBindingUtil.setContentView(this, R.layout.activity_recommend)

        val verificationSelectViewModel: VerificationSelectViewModel = ViewModelProvider(this)[VerificationSelectViewModel::class.java]

        activityRecommendBinding.ivTravelRecommendGalleryFirst.setOnClickListener {

            /** 첫 번째 사진을 선택하였으므로 FirstGalleryChoiceStatus Type 선택 **/
            selectedImageType = SelectedImageType.FirstGalleryChoiceStatus

            val intent = Intent(Intent.ACTION_PICK)
            intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
            startActivityForResult(intent, 200)
        }

        activityRecommendBinding.ivTravelRecommendGallerySecond.setOnClickListener {
            /** 두 번째 사진을 선택하였으므로 SecondGalleryChoiceStatus Type 선택 **/
            selectedImageType = SelectedImageType.SecondGalleryChoiceStatus

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

            firebaseStorageManager.uploadImage(bitmap) { travelRecommendImageUrl ->

                val travelRecommendCountry =
                    activityRecommendBinding.etTravelRecommendCountry.text.toString()
                val travelRecommendImpression =
                    activityRecommendBinding.etTravelRecommendImpression.text.toString()
                val travelRecommendAloneStatus =
                    activityRecommendBinding.tvTravelRecommendAloneStatus.text.toString()

                val travelRecommend = hashMapOf(
                    "travelRecommendCountry" to travelRecommendCountry,
                    "travelRecommendImageUrl" to travelRecommendImageUrl,
                    "travelRecommendImpression" to travelRecommendImpression,
                    "travelRecommendAloneStatus" to travelRecommendAloneStatus,
                )

                recommendTravelList.add(
                    TravelRecommend(
                        travelRecommendCountry,
                        travelRecommendImageUrl.toString(),
                        travelRecommendAloneStatus,
                        travelRecommendImpression
                    )
                )

                db.collection("travelRecommends").add(travelRecommend)
                    .addOnSuccessListener {

                    }
                    .addOnFailureListener {
                        Log.d("RecommendActivityDBInsert", "등록 실패")
                    }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 200 && resultCode == Activity.RESULT_OK) {
            val selectedImageUri: Uri? = data?.data

            bitmap = getBitmapFromUri(selectedImageUri!!)

            Log.d("RecommendActivityImageUri", selectedImageUri.toString())

            if (selectedImageType == SelectedImageType.FirstGalleryChoiceStatus) {
                activityRecommendBinding.ivTravelRecommendGalleryFirst.setImageURI(selectedImageUri)
            } else {
                activityRecommendBinding.ivTravelRecommendGallerySecond.setImageURI(selectedImageUri)
            }
        }
    }

    private fun getBitmapFromUri(uri: Uri): Bitmap {
        return if (Build.VERSION.SDK_INT < 28) {
            MediaStore.Images.Media.getBitmap(contentResolver, uri)
        } else {
            val source = ImageDecoder.createSource(contentResolver, uri)
            ImageDecoder.decodeBitmap(source)
        }
    }
}