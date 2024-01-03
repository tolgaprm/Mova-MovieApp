package com.prmto.data.remote.mapper.detail.tv

import com.prmto.core_data.util.HandleUtils
import com.prmto.core_data.util.orZero
import com.prmto.data.remote.dto.detail.tv.TvDetailDto
import com.prmto.data.remote.mapper.credit.toCredit
import com.prmto.data.remote.mapper.watchProvider.toWatchProviderItem
import com.prmto.data.remote.util.DetailHandleUtils
import com.prmto.domain.models.detail.tv.TvDetail

fun TvDetailDto.toTvDetail(
    countryIsoCode: String
): TvDetail {
    return TvDetail(
        id = id.orZero(),
        genres = genres.orEmpty(),
        createdBy = createdBy?.toListOfCreatedBy(),
        numberOfSeasons = numberOfSeasons.orZero(),
        originalName = originalName.orEmpty(),
        name = name.orEmpty(),
        overview = overview.orEmpty(),
        posterPath = posterPath,
        voteAverage = voteAverage.orZero(),
        formattedVoteCount = HandleUtils.formatVoteCount(voteCount),
        watchProviders = watchProviders?.results?.toWatchProviderItem(countryIsoCode = countryIsoCode),
        credit = credits?.toCredit(),
        genresBySeparatedByComma = HandleUtils.getGenresBySeparatedByComma(genreList = genres),
        ratingValue = DetailHandleUtils.calculateRatingBarValue(voteAverage),
        releaseDate = DetailHandleUtils.convertTvSeriesReleaseDateBetweenFirstAndLastDate(
            firstAirDate = firstAirDate,
            lastAirDate = lastAirDate,
            status = status
        ),
        status = status.orEmpty(),
        firstAirDate = firstAirDate.orEmpty(),
        lastAirDate = lastAirDate.orEmpty()
    )
}