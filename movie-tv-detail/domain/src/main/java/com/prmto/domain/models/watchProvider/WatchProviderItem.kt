package com.prmto.domain.models.watchProvider

data class WatchProviderItem(
    val stream: List<WatchProviderItemInfo>,
    val rent: List<WatchProviderItemInfo>,
    val buy: List<WatchProviderItemInfo>
)

data class WatchProviderItemInfo(
    val logoPath: String,
    val providerName: String,
)