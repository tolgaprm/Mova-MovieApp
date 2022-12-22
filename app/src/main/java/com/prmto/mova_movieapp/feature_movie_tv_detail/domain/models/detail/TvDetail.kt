package com.prmto.mova_movieapp.feature_movie_tv_detail.domain.models.detail

import com.prmto.mova_movieapp.core.data.dto.Genre
import com.prmto.mova_movieapp.feature_movie_tv_detail.data.dto.detail.tv.Season
import com.prmto.mova_movieapp.feature_movie_tv_detail.data.dto.watch_provider.WatchProviders
import com.prmto.mova_movieapp.feature_movie_tv_detail.domain.models.credit.Credit

data class TvDetail(
    val id: Int,
    val genres: List<Genre>,
    val firstAirDate: String,
    val createdBy: List<CreatedBy>,
    val lastAirDate: String,
    val numberOfSeasons: Int,
    val originalName: String,
    val name: String,
    val overview: String,
    val posterPath: String?,
    val seasons: List<Season>,
    val status: String,
    val voteAverage: Double,
    val voteCount: Int,
    var ratingValue: Float = 0f,
    var releaseDate: String = "",
    val credit: Credit,
    val watchProviders: WatchProviders
)
