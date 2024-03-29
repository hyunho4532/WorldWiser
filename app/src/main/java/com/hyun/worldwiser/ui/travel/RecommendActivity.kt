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
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.hyun.worldwiser.FirebaseStorageManager
import com.hyun.worldwiser.R
import com.hyun.worldwiser.databinding.ActivityRecommendBinding
import com.hyun.worldwiser.model.TravelRecommend
import com.hyun.worldwiser.type.SelectedImageType
import com.hyun.worldwiser.viewmodel.VerificationSelectViewModel
import java.security.PrivateKey

class RecommendActivity : AppCompatActivity() {

    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()
    private lateinit var activityRecommendBinding: ActivityRecommendBinding
    private val recommendTravelList: ArrayList<TravelRecommend>  = ArrayList()

    private var imageUri: Uri? = null
    private lateinit var selectedImageType: SelectedImageType

    private var travelRecommendImageUrlBitmapFirst: Bitmap? = null
    private var travelRecommendImageUrlBitmapSecond: Bitmap? = null
    private lateinit var travelRecommendAuthNickname: String

    private val firebaseStorageManager = FirebaseStorageManager()
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

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
            travelRecommendAuthNickname = nickname

            activityRecommendBinding.tvNicknameAuthWhoTravelRecommend.text = nickname + "님의 추천 여행지는?"
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

            val bitmaps = mutableListOf<Bitmap?>()

            if (travelRecommendImageUrlBitmapFirst != null && travelRecommendImageUrlBitmapSecond == null) {
                bitmaps.add(travelRecommendImageUrlBitmapFirst)
            }

            if (travelRecommendImageUrlBitmapFirst != null && travelRecommendImageUrlBitmapSecond != null) {
                bitmaps.add(travelRecommendImageUrlBitmapFirst)
                bitmaps.add(travelRecommendImageUrlBitmapSecond)
            }

            firebaseStorageManager.uploadImages(bitmaps) { imageUrls ->
                // 이미지 업로드 작업 완료될 때 실행되는 부분
                val travelRecommendCountry =
                    activityRecommendBinding.etTravelRecommendCountry.text.toString()
                val travelRecommendImpression =
                    activityRecommendBinding.etTravelRecommendImpression.text.toString()
                val travelRecommendAloneStatus =
                    activityRecommendBinding.tvTravelRecommendAloneStatus.text.toString()
                val travelRecommendAuthUid = auth.currentUser!!.uid
                val travelRecommendFavoriteCount = 0

                Log.d("travelRecommendImageUrls", imageUrls.size.toString())

                val travelRecommend = hashMapOf(
                    "travelRecommendAuthUid" to travelRecommendAuthUid,
                    "travelRecommendNickname" to travelRecommendAuthNickname,
                    "travelRecommendCountry" to travelRecommendCountry,
                    "travelRecommendImpression" to travelRecommendImpression,

                    "travelRecommendImageUrls" to when (imageUrls.size) {
                        1 -> imageUrls[0].toString().trim()
                        2 -> imageUrls.map { it.toString() }
                        else -> ""
                    },

                    "travelRecommendAloneStatus" to travelRecommendAloneStatus,
                    "travelRecommendFavoriteCount" to travelRecommendFavoriteCount
                )

                Log.d("RecommendActivityImageUrl", imageUrls.toString())

                db.collection("travelRecommends").add(travelRecommend)
                    .addOnSuccessListener {
                        Log.d("RecommendActivityDBInsert", "Success to register")
                    }
                    .addOnFailureListener {
                        Log.d("RecommendActivityDBInsert", "Failed to register")
                    }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 200 && resultCode == Activity.RESULT_OK) {
            val selectedImageUri: Uri? = data?.data

            if (selectedImageType == SelectedImageType.FirstGalleryChoiceStatus) {
                travelRecommendImageUrlBitmapFirst = getBitmapFromUri(selectedImageUri!!)
            } else {
                travelRecommendImageUrlBitmapSecond = getBitmapFromUri(selectedImageUri!!)
            }

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