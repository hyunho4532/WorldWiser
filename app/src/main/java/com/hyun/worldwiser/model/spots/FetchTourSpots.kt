package com.hyun.worldwiser.model.spots

import TourApiService
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

suspend fun fetchTourSpots(): TourSpotResponse {

    return withContext(Dispatchers.IO) {

        val gson: Gson = GsonBuilder()
            .setLenient()
            .create()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://apis.data.go.kr/B551011/KorService1/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()

        val service = retrofit.create(TourApiService::class.java)

        try {
            service.getTourSpots(
                serviceKey = "KmXwF4GXnRJiiNY68ky5tSl88Zi3IsotZW3VlDC%2BEGf472pLAf%2FgWmsnJDq9d22bOLATJFTTixhypw6BuSDJug%3D%3D",
                numOfRows = 10,
                pageNo = 1,
                mobileOS = "ETC",
                mobileApp = "AppTest",
                listYN = "Y",
                arrange = "A",
                keyword = "강원",
                contentTypeId = 12
            )
        } catch (e: Exception) {
            throw e
        }
    }
}