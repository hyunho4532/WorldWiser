package com.hyun.worldwiser.model

import com.google.firebase.auth.FirebaseAuth

data class TravelRecommend(
    val travelRecommendAuthUid: String,
    val travelRecommendAuthNickname: String,
    val travelRecommendCountry: String,
    val travelRecommendImageUrlFirst: String?,
    val travelRecommendImageUrlSecond: String?,
    val travelAloneStatus: String,
    val travelRecommendImpression: String,
    val travelRecommendFavoriteCount: Int
)