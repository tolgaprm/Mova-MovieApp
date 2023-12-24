package com.prmto.mova_movieapp.feature_movie_tv_detail.presentation.detail

import com.prmto.mova_movieapp.core.domain.models.Movie
import com.prmto.mova_movieapp.core.domain.models.TvSeries
import com.prmto.mova_movieapp.feature_movie_tv_detail.domain.movie.model.MovieDetail
import com.prmto.mova_movieapp.feature_movie_tv_detail.domain.tv.model.TvDetail

data class DetailState(
    val isLoading: Boolean = false,
    val videosLoading: Boolean = false,
    val recommendationLoading: Boolean = false,
    val movieRecommendation: List<Movie>? = null,
    val tvRecommendation: List<TvSeries>? = null,
    val movieDetail: MovieDetail? = null,
    val tvDetail: TvDetail? = null,
    val doesAddFavorite: Boolean = false,
    val doesAddWatchList: Boolean = false
)

