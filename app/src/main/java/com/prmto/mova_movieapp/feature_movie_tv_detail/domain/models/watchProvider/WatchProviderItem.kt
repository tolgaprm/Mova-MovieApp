package com.prmto.mova_movieapp.feature_movie_tv_detail.domain.models.watchProvider

data class WatchProviderItem(
    val stream: List<WatchProviderItemInfo>,
    val rent: List<WatchProviderItemInfo>,
    val buy: List<WatchProviderItemInfo>
)

data class WatchProviderItemInfo(
    val logoPath: String,
    val providerName: String,
)