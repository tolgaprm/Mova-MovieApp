package com.prmto.mova_movieapp.presentation.detail

import androidx.annotation.StringRes
import com.prmto.mova_movieapp.domain.models.MovieDetail
import com.prmto.mova_movieapp.domain.models.TvDetail

data class DetailState(
    val loading: Boolean = false,
    val movieDetail: MovieDetail? = null,
    val tvDetail: TvDetail? = null,
    @StringRes val errorId: Int? = null
)
