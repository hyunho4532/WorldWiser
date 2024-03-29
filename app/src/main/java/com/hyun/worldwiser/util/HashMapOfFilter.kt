package com.hyun.worldwiser.util

class HashMapOfFilter {
    fun insertVerificationDataFromMap (
        authUid: String,
        countryFavorite: String,
        travelPreferences: String,
        transport: String,
        nickname: String
    ): HashMap<String, String> {

        return hashMapOf(
            "authUid" to authUid,
            "country_favorite" to countryFavorite,
            "travel_preferences" to travelPreferences,
            "transport" to transport,
            "nickname" to nickname
        )
    }
}