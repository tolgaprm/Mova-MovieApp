package com.prmto.mova_movieapp.feature_mylist.presentation

import com.prmto.mova_movieapp.core.domain.models.movie.Movie
import com.prmto.mova_movieapp.core.domain.models.tv.TvSeries

sealed class ListEvent {
    data class SelectedTab(val listTab: ListTab) : ListEvent()
    data class UpdateListType(val chipType: ChipType) : ListEvent()
    data class ClickedMovieItem(val movie: Movie) : ListEvent()
    data class ClickedTvSeriesItem(val tvSeries: TvSeries) : ListEvent()
}
