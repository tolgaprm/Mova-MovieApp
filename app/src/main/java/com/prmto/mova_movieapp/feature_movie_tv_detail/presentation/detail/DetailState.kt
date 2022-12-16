package com.prmto.mova_movieapp.feature_movie_tv_detail.presentation.detail

import com.prmto.mova_movieapp.core.util.Constants.DETAIL_DEFAULT_ID
import com.prmto.mova_movieapp.feature_movie_tv_detail.domain.models.detail.MovieDetail
import com.prmto.mova_movieapp.feature_movie_tv_detail.domain.models.detail.TvDetail

data class DetailState(
    val loading: Boolean = false,
    val movieDetail: MovieDetail? = null,
    val tvDetail: TvDetail? = null,
    val movieId: Int = DETAIL_DEFAULT_ID,
    val tvId: Int = DETAIL_DEFAULT_ID
)
