package com.prmto.mova_movieapp.feature_movie_tv_detail.data.dto.watch_provider

import com.squareup.moshi.Json

data class WatchProviderItem(
    val link: String,
    @Json(name = "flatrate") val flatRate: List<WatchProviderItemDetail>?,
    val rent: List<WatchProviderItemDetail>?,
    val buy: List<WatchProviderItemDetail>?,
    val free: List<WatchProviderItemDetail>?,
)

data class WatchProviderItemDetail(
    @Json(name = "display_priority") val displayPriority: Int,
    @Json(name = "logo_path") val logoPath: String,
    @Json(name = "provider_id") val providerId: Int,
    @Json(name = "provider_name") val providerName: String,
)