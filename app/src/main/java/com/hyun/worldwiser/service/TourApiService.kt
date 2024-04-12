package com.hyun.worldwiser.service

import com.hyun.worldwiser.model.TourSpot
import retrofit2.http.GET

interface TourApiService {

    @GET("tour_spots")
    suspend fun getTourSpots(): List<TourSpot>
}