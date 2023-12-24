package com.prmto.mova_movieapp.feature_movie_tv_detail.data.mapper

import com.prmto.mova_movieapp.feature_movie_tv_detail.data.dto.watch_provider.Country
import com.prmto.mova_movieapp.feature_movie_tv_detail.data.dto.watch_provider.WatchProviderItemDetailDto
import com.prmto.mova_movieapp.feature_movie_tv_detail.data.dto.watch_provider.WatchProviderRegionDto
import com.prmto.mova_movieapp.feature_movie_tv_detail.domain.models.watchProvider.WatchProviderItem
import com.prmto.mova_movieapp.feature_movie_tv_detail.domain.models.watchProvider.WatchProviderItemInfo

fun WatchProviderRegionDto.toWatchProviderItem(
    countryIsoCode: String
): WatchProviderItem {
    val providerRegionDto = when (countryIsoCode) {
        Country.US.isoCode -> this.us
        Country.DE.isoCode -> this.de
        Country.ES.isoCode -> this.es
        Country.FR.isoCode -> this.fr
        Country.TR.isoCode -> this.tr
        else -> this.us
    }

    return WatchProviderItem(
        stream = providerRegionDto?.flatRate.toWatchProviderItemDetail(),
        rent = providerRegionDto?.rent.toWatchProviderItemDetail(),
        buy = providerRegionDto?.rent.toWatchProviderItemDetail()
    )
}

fun List<WatchProviderItemDetailDto>?.toWatchProviderItemDetail(): List<WatchProviderItemInfo> {
    return this?.sortedBy { it.displayPriority }?.take(2)?.map {
        WatchProviderItemInfo(
            logoPath = it.logoPath,
            providerName = it.providerName
        )
    } ?: emptyList()
}