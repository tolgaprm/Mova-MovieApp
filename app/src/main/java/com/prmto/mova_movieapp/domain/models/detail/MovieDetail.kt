package com.prmto.mova_movieapp.domain.models.detail

import com.prmto.mova_movieapp.data.models.Genre
import com.prmto.mova_movieapp.data.models.watch_provider.WatchProviders
import com.prmto.mova_movieapp.domain.models.credit.Credit

data class MovieDetail(
    val id: Int,
    val genres: List<Genre>,
    val imdbId: String?,
    val originalTitle: String,
    val title: String,
    val overview: String?,
    val posterPath: String?,
    val releaseDate: String,
    val runtime: Int?,
    val voteAverage: Double,
    val voteCount: Int,
    var convertedRuntime: Map<String, String> = emptyMap(),
    val credit: Credit,
    var ratingValue: Float = 0f,
    val watchProviders: WatchProviders
)
