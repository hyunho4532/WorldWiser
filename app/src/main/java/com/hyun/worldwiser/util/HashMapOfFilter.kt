package com.hyun.worldwiser.util

class HashMapOfFilter {
    fun insertVerificationDataFromMap (
        countryFavorite: String,
        travelPreferences: String,
        transport: String,
        nickname: String
    ): HashMap<String, String> {

        return hashMapOf(
            "country_favorite" to countryFavorite,
            "travel_preferences" to travelPreferences,
            "transport" to transport,
            "nickname" to nickname
        )
    }
}