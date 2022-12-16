package com.prmto.mova_movieapp.core.data.dto

import com.squareup.moshi.Json


data class ApiResponse<T>(
    val page: Int,
    val results: List<T>,
    @Json(name = "total_results") val totalResults: Int,
    @Json(name = "total_pages") val totalPages: Int
)
