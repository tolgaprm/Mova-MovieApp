package com.prmto.mova_movieapp.feature_movie_tv_detail.presentation.detail

import com.prmto.mova_movieapp.feature_movie_tv_detail.domain.models.detail.MovieDetail
import com.prmto.mova_movieapp.feature_movie_tv_detail.domain.models.detail.TvDetail
import com.prmto.mova_movieapp.feature_movie_tv_detail.domain.models.detail.video.Videos

data class DetailState(
    val loading: Boolean = false,
    val videosLoading: Boolean = false,
    val movieDetail: MovieDetail? = null,
    val tvDetail: TvDetail? = null,
    val videos: Videos? = null
)
