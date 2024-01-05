package com.prmto.data.remote.mapper.watchProvider

import com.prmto.data.remote.dto.watchProvider.Country
import com.prmto.data.remote.dto.watchProvider.WatchProviderItemDetailDto
import com.prmto.data.remote.dto.watchProvider.WatchProviderRegionDto
import com.prmto.domain.models.watchProvider.WatchProviderItem
import com.prmto.domain.models.watchProvider.WatchProviderItemInfo

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