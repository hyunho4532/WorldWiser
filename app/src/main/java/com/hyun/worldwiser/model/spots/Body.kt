package com.hyun.worldwiser.model.spots

data class Body(
    val items: Items,
    val numOfRows: Long,
    val pageNo: Long,
    val totalCount: Long,
)