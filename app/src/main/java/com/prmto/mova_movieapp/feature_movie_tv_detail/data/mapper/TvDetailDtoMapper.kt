package com.prmto.mova_movieapp.feature_movie_tv_detail.data.mapper

import com.prmto.mova_movieapp.core.data.orZero
import com.prmto.mova_movieapp.feature_movie_tv_detail.data.dto.detail.tv.TvDetailDto
import com.prmto.mova_movieapp.feature_movie_tv_detail.domain.tv.model.TvDetail

fun TvDetailDto.toTvDetail(
    countryIsoCode: String
): TvDetail {
    return TvDetail(
        id = id.orZero(),
        genres = genres.orEmpty(),
        firstAirDate = firstAirDate.orEmpty(),
        lastAirDate = lastAirDate.orEmpty(),
        createdBy = createdBy?.toListOfCreatedBy(),
        numberOfSeasons = numberOfSeasons.orZero(),
        originalName = originalName.orEmpty(),
        name = name.orEmpty(),
        overview = overview.orEmpty(),
        posterPath = posterPath,
        seasons = seasons.orEmpty(),
        status = status.orEmpty(),
        voteAverage = voteAverage.orZero(),
        voteCount = voteCount.orZero(),
        watchProviders = watchProviders?.results?.toWatchProviderItem(countryIsoCode = countryIsoCode),
        credit = credits?.toCredit()
    )
}