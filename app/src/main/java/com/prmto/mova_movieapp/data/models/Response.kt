package com.prmto.mova_movieapp.data.models

import com.squareup.moshi.Json


data class Response<T>(
    val page: Int,
    val results: List<T>,
    @Json(name = "total_results") val totalResults: Int,
    @Json(name = "total_pages") val totalPages: Int
)
