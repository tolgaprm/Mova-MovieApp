package com.prmto.mova_movieapp.feature_movie_tv_detail.data.mapper

import com.prmto.mova_movieapp.core.data.orZero
import com.prmto.mova_movieapp.feature_movie_tv_detail.data.dto.detail.movie.MovieDetailDto
import com.prmto.mova_movieapp.feature_movie_tv_detail.domain.models.detail.MovieDetail

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
        runtime = runtime,
        voteAverage = voteAverage.orZero(),
        voteCount = voteCount.orZero(),
        credit = credits?.toCredit(),
        watchProviders = watchProviders?.results?.toWatchProviderItem(countryIsoCode = countryIsoCode),
    )
}