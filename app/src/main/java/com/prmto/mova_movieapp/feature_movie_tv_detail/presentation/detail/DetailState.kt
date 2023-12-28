package com.prmto.mova_movieapp.feature_movie_tv_detail.presentation.detail

import com.prmto.mova_movieapp.core.domain.models.movie.Movie
import com.prmto.mova_movieapp.core.domain.models.tv.TvSeries
import com.prmto.mova_movieapp.feature_movie_tv_detail.domain.models.detail.movie.MovieDetail
import com.prmto.mova_movieapp.feature_movie_tv_detail.domain.models.detail.tv.TvDetail

data class DetailState(
    val isLoading: Boolean = false,
    val videosLoading: Boolean = false,
    val recommendationLoading: Boolean = false,
    val movieRecommendation: List<Movie>? = null,
    val tvRecommendation: List<TvSeries>? = null,
    val movieDetail: MovieDetail? = null,
    val tvDetail: TvDetail? = null,
    val doesAddFavorite: Boolean = false,
    val doesAddWatchList: Boolean = false,
    val selectedTab: SelectableTab = SelectableTab.RECOMMENDATION_TAB,
    val movieId: Int? = null,
    val tvId: Int? = null,
)

enum class SelectableTab {
    RECOMMENDATION_TAB,
    TRAILER_TAB
}

fun DetailState.isSelectedRecommendationTab(): Boolean {
    return this.selectedTab == SelectableTab.RECOMMENDATION_TAB
}

fun DetailState.isSelectedTrailerTab(): Boolean {
    return this.selectedTab == SelectableTab.TRAILER_TAB
}

fun DetailState.isNotNullTvDetail(): Boolean {
    return tvId != null
}