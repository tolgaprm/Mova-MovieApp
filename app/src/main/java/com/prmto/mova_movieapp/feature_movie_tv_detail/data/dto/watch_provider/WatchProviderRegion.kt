package com.prmto.mova_movieapp.feature_movie_tv_detail.data.dto.watch_provider

import com.squareup.moshi.Json

data class WatchProviderRegion(
    @Json(name = "TR") val tr: WatchProviderItem?,
    @Json(name = "DE") val de: WatchProviderItem?,
    @Json(name = "US") val us: WatchProviderItem?,
)

