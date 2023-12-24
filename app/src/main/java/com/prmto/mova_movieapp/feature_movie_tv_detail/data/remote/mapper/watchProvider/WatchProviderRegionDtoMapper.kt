package com.prmto.mova_movieapp.feature_movie_tv_detail.data.remote.mapper.watchProvider

import com.prmto.mova_movieapp.feature_movie_tv_detail.data.remote.dto.watchProvider.WatchProviderItemDetailDto
import com.prmto.mova_movieapp.feature_movie_tv_detail.data.remote.dto.watchProvider.WatchProviderRegionDto
import com.prmto.mova_movieapp.feature_movie_tv_detail.domain.models.watchProvider.WatchProviderItem
import com.prmto.mova_movieapp.feature_movie_tv_detail.domain.models.watchProvider.WatchProviderItemInfo

fun WatchProviderRegionDto.toWatchProviderItem(
    countryIsoCode: String
): WatchProviderItem {
    val providerRegionDto = when (countryIsoCode) {
        com.prmto.mova_movieapp.feature_movie_tv_detail.data.remote.dto.watchProvider.Country.US.isoCode -> this.us
        com.prmto.mova_movieapp.feature_movie_tv_detail.data.remote.dto.watchProvider.Country.DE.isoCode -> this.de
        com.prmto.mova_movieapp.feature_movie_tv_detail.data.remote.dto.watchProvider.Country.ES.isoCode -> this.es
        com.prmto.mova_movieapp.feature_movie_tv_detail.data.remote.dto.watchProvider.Country.FR.isoCode -> this.fr
        com.prmto.mova_movieapp.feature_movie_tv_detail.data.remote.dto.watchProvider.Country.TR.isoCode -> this.tr
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