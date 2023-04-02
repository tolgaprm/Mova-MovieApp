package com.prmto.mova_movieapp.feature_movie_tv_detail.data.mapper

import com.prmto.mova_movieapp.feature_movie_tv_detail.data.dto.detail.tv.TvDetailDto
import com.prmto.mova_movieapp.feature_movie_tv_detail.domain.models.detail.TvDetail

fun TvDetailDto.toTvDetail(): TvDetail {
    return TvDetail(
        id = id,
        genres = genres,
        firstAirDate = firstAirDate,
        lastAirDate = lastAirDate,
        createdBy = createdBy.toListOfCreatedBy(),
        numberOfSeasons = numberOfSeasons,
        originalName = originalName,
        name = name,
        overview = overview,
        posterPath = posterPath,
        seasons = seasons,
        status = status,
        voteAverage = voteAverage,
        voteCount = voteCount,
        watchProviders = watchProviders,
        credit = credits.toCredit()
    )
}