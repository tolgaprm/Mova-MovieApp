package com.prmto.mova_movieapp.feature_movie_tv_detail.data.remote.mapper.detail.tv

import com.prmto.mova_movieapp.core.data.util.HandleUtils
import com.prmto.mova_movieapp.core.data.util.orZero
import com.prmto.mova_movieapp.feature_movie_tv_detail.data.remote.dto.detail.tv.TvDetailDto
import com.prmto.mova_movieapp.feature_movie_tv_detail.data.remote.mapper.credit.toCredit
import com.prmto.mova_movieapp.feature_movie_tv_detail.data.remote.mapper.watchProvider.toWatchProviderItem
import com.prmto.mova_movieapp.feature_movie_tv_detail.data.remote.util.DetailHandleUtils
import com.prmto.mova_movieapp.feature_movie_tv_detail.domain.models.detail.tv.TvDetail

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
        seasons = seasons.orEmpty(),
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