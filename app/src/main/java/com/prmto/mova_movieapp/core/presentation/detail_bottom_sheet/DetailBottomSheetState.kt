package com.prmto.mova_movieapp.core.presentation.detail_bottom_sheet

import com.prmto.core_domain.models.movie.Movie
import com.prmto.core_domain.models.tv.TvSeries

data class DetailBottomSheetState(
    val movie: Movie? = null,
    val tvSeries: TvSeries? = null,
    val doesAddFavorite: Boolean = false,
    val doesAddWatchList: Boolean = false
)
