package com.prmto.mova_movieapp.presentation.detail

import com.prmto.mova_movieapp.domain.models.detail.MovieDetail
import com.prmto.mova_movieapp.domain.models.detail.TvDetail
import com.prmto.mova_movieapp.presentation.util.UiText

data class DetailState(
    val loading: Boolean = false,
    val movieDetail: MovieDetail? = null,
    val tvDetail: TvDetail? = null,
    val error: UiText? = null
)
