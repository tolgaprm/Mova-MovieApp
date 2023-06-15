package com.prmto.mova_movieapp.feature_upcoming.data.remote.dto

import com.squareup.moshi.Json

data class UpComingApiResponse<T>(
    val dates: Dates,
    val page: Int,
    val results: List<T>,
    @Json(name = "total_results") val totalResults: Int,
    @Json(name = "total_pages") val totalPages: Int
)


data class Dates(
    val maximum: String,
    val minimum: String
)