package com.prmto.mova_movieapp.feature_movie_tv_detail.data.remote.dto.watchProvider

import com.squareup.moshi.Json

data class WatchProviderRegionDto(
    @Json(name = "TR") val tr: WatchProviderItemDto?,
    @Json(name = "ES") val es: WatchProviderItemDto?,
    @Json(name = "DE") val de: WatchProviderItemDto?,
    @Json(name = "FR") val fr: WatchProviderItemDto?,
    @Json(name = "US") val us: WatchProviderItemDto?,
)