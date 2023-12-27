package com.hyun.worldwiser.model

data class Country (
    val countryFavorite: String,


) {
    override fun toString(): String {
        return countryFavorite
    }
}