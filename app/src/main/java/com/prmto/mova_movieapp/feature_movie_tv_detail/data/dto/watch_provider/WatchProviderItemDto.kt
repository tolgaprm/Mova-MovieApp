package com.prmto.mova_movieapp.feature_movie_tv_detail.data.dto.watch_provider

import com.squareup.moshi.Json

data class WatchProviderItemDto(
    val link: String,
    @Json(name = "flatrate") val flatRate: List<WatchProviderItemDetailDto>?,
    val rent: List<WatchProviderItemDetailDto>?,
    val buy: List<WatchProviderItemDetailDto>?,
    val free: List<WatchProviderItemDetailDto>?,
)

data class WatchProviderItemDetailDto(
    @Json(name = "display_priority") val displayPriority: Int,
    @Json(name = "logo_path") val logoPath: String,
    @Json(name = "provider_id") val providerId: Int,
    @Json(name = "provider_name") val providerName: String,
)