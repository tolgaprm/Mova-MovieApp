package com.prmto.core_data.remote.dto

import com.squareup.moshi.Json

data class ApiResponse<T>(
    val page: Int,
    val results: List<T>,
    @Json(name = "total_results") val totalResults: Int,
    @Json(name = "total_pages") val totalPages: Int
)
