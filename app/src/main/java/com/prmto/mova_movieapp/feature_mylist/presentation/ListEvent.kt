package com.prmto.mova_movieapp.feature_mylist.presentation

import com.prmto.mova_movieapp.core.domain.models.Movie
import com.prmto.mova_movieapp.core.domain.models.TvSeries

sealed class ListEvent {
    data class SelectedTab(val listTab: ListTab) : ListEvent()
    data class UpdateListType(val listType: ListType) : ListEvent()
    data class ClickedMovieItem(val movie: Movie) : ListEvent()
    data class ClickedTvSeriesItem(val tvSeries: TvSeries) : ListEvent()
}
