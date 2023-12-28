package com.prmto.mova_movieapp.feature_movie_tv_detail.data.remote.mapper.detail.movie

import com.prmto.mova_movieapp.core.data.util.HandleUtils
import com.prmto.mova_movieapp.core.data.util.orZero
import com.prmto.mova_movieapp.feature_movie_tv_detail.data.remote.dto.detail.movie.MovieDetailDto
import com.prmto.mova_movieapp.feature_movie_tv_detail.data.remote.mapper.credit.toCredit
import com.prmto.mova_movieapp.feature_movie_tv_detail.data.remote.mapper.watchProvider.toWatchProviderItem
import com.prmto.mova_movieapp.feature_movie_tv_detail.data.remote.util.DetailHandleUtils
import com.prmto.mova_movieapp.feature_movie_tv_detail.domain.models.detail.movie.MovieDetail

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
        voteCount = voteCount.orZero(),
        credit = credits?.toCredit(),
        watchProviders = watchProviders?.results?.toWatchProviderItem(countryIsoCode = countryIsoCode),
        genresBySeparatedByComma = HandleUtils.getGenresBySeparatedByComma(genreList = genres),
        ratingValue = DetailHandleUtils.calculateRatingBarValue(voteAverage = voteAverage.orZero()),
        convertedRuntime = DetailHandleUtils.convertRuntimeAsHourAndMinutes(runtime),
    )
}