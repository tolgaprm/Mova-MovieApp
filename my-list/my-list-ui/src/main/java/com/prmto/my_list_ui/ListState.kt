package com.prmto.my_list_ui

import com.prmto.core_domain.models.movie.Movie
import com.prmto.core_domain.models.tv.TvSeries

data class ListState(
    val selectedTab: ListTab = ListTab.FAVORITELIST,
    val chipType: ChipType = ChipType.MOVIE,
    val isLoading: Boolean = false,
    val movieList: List<Movie> = emptyList(),
    val tvSeriesList: List<TvSeries> = emptyList()
)


enum class ListTab {
    WATCHLIST,
    FAVORITELIST
}

fun ListTab.isWatchList(): Boolean = this == ListTab.WATCHLIST
fun ListTab.isFavoriteList(): Boolean = this == ListTab.FAVORITELIST
