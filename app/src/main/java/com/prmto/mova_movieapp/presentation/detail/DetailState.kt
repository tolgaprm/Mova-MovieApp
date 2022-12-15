package com.prmto.mova_movieapp.presentation.detail

import com.prmto.mova_movieapp.domain.models.detail.MovieDetail
import com.prmto.mova_movieapp.domain.models.detail.TvDetail
import com.prmto.mova_movieapp.util.Constants.DETAIL_DEFAULT_ID

data class DetailState(
    val loading: Boolean = false,
    val movieDetail: MovieDetail? = null,
    val tvDetail: TvDetail? = null,
    val movieId: Int = DETAIL_DEFAULT_ID,
    val tvId: Int = DETAIL_DEFAULT_ID
)
