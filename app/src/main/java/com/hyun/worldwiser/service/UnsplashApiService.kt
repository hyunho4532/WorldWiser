package com.hyun.worldwiser.service

import com.hyun.worldwiser.model.UnsplashPhoto
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface UnsplashApiService {
    @GET("/photos/random")
    fun getRandomPhotos(
        @Query("client_id") clientId: String,
        @Query("count") count: Int
    ): Call<List<UnsplashPhoto>>
}