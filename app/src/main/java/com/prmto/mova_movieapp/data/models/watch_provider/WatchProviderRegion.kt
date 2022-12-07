package com.prmto.mova_movieapp.data.models.watch_provider

import com.squareup.moshi.Json

data class WatchProviderRegion(
    @Json(name = "TR") val tr: WatchProviderItem?,
    @Json(name = "ES") val es: WatchProviderItem?,
    @Json(name = "DE") val de: WatchProviderItem?,
    @Json(name = "FR") val fr: WatchProviderItem?,
    @Json(name = "EN") val en: WatchProviderItem?,
    @Json(name = "US") val us: WatchProviderItem?,
)

