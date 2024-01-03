package com.prmto.data.remote.mapper.detail.movie

import com.prmto.core_data.util.HandleUtils
import com.prmto.core_data.util.orZero
import com.prmto.data.remote.dto.detail.movie.MovieDetailDto
import com.prmto.data.remote.mapper.credit.toCredit
import com.prmto.data.remote.mapper.watchProvider.toWatchProviderItem
import com.prmto.data.remote.util.DetailHandleUtils
import com.prmto.domain.models.detail.movie.MovieDetail

fun MovieDetailDto.toMovieDetail(countryIsoCode: String): MovieDetail {
    return MovieDetail(
        id = id.orZero(),
        genres = genres.orEmpty(),
        imdbId = imdbId,
        originalTitle = originalTitle.orEmpty(),
        title = title.orEmpty(),
        overview = overview,
        posterPath = posterPath,
        releaseDate = releaseDate.orEmpty(),
        voteAverage = voteAverage.orZero(),
        formattedVoteCount = HandleUtils.formatVoteCount(voteCount),
        credit = credits?.toCredit(),
        watchProviders = watchProviders?.results?.toWatchProviderItem(countryIsoCode = countryIsoCode),
        genresBySeparatedByComma = HandleUtils.getGenresBySeparatedByComma(genreList = genres),
        ratingValue = DetailHandleUtils.calculateRatingBarValue(voteAverage = voteAverage.orZero()),
        convertedRuntime = DetailHandleUtils.convertRuntimeAsHourAndMinutes(runtime),
    )
}