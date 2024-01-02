package com.hyun.worldwiser.viewmodel

import android.annotation.SuppressLint
import android.widget.Adapter
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.hyun.worldwiser.adapter.ImageAdapter
import com.hyun.worldwiser.databinding.ActivityInsertBinding
import com.hyun.worldwiser.model.UnsplashPhoto
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UnsplashApiInsertViewModel : ViewModel() {

    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    private lateinit var imageAdapter: ImageAdapter
    private lateinit var imageGetUrl: String

    fun loadUnsplashAPIEnqueue (
        call: Call<List<UnsplashPhoto>>,
        activityInsertBinding: ActivityInsertBinding,
        imageUrls: MutableList<String>,
        verificationInsertViewModel: VerificationInsertViewModel,
        listener: (ImageAdapter) -> Unit
    ) {

        call.enqueue(object : Callback<List<UnsplashPhoto>> {
            @SuppressLint("NotifyDataSetChanged")
            override fun onResponse(
                call: Call<List<UnsplashPhoto>>,
                response: Response<List<UnsplashPhoto>>
            ) {
                if (!response.isSuccessful) {
                    return
                }

                val photos = response.body()
                photos?.forEach { photo ->
                    imageUrls.add(photo.urls.regular)
                }

                imageAdapter = ImageAdapter(imageUrls) { imageUrl ->
                    imageGetUrl = imageUrl

                    activityInsertBinding.btnTravelInsert.setOnClickListener {
                        verificationInsertViewModel.insertVerificationData()
                        val travelUpdate = hashMapOf (
                            "authUid" to auth.currentUser!!.uid,
                            "imageUrl" to imageGetUrl,
                            "country" to activityInsertBinding.etCountryTravel.text.toString(),
                            "countryStatus" to activityInsertBinding.tvTravelStatus.text.toString(),
                            "startDay" to activityInsertBinding.etTravelCalendarStart.text.toString(),
                            "endDay" to activityInsertBinding.etTravelCalendarEnd.text.toString()
                        )

                        db.collection("travelInserts").add(travelUpdate)
                            .addOnSuccessListener {

                            }
                    }
                }
                
                listener.invoke(imageAdapter)
            }

            override fun onFailure(call: Call<List<UnsplashPhoto>>, t: Throwable) {

            }
        })
    }
}