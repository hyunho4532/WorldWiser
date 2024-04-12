package com.hyun.worldwiser.model.spots

import org.json.JSONObject

data class TourSpotResponse (
    val firstImage: String?
) {
    companion object {
        fun fromJson(json: JSONObject): TourSpotResponse {
            return TourSpotResponse(
                firstImage = json["firstimage"].toString()
            )
        }
    }
}