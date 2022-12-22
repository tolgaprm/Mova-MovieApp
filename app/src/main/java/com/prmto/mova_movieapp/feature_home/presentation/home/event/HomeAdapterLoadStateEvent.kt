package com.prmto.mova_movieapp.feature_home.presentation.home.event

import com.prmto.mova_movieapp.core.presentation.util.UiText

sealed class HomeAdapterLoadStateEvent {

    data class PagingError(val uiText: UiText) : HomeAdapterLoadStateEvent()

    object NowPlayingLoading : HomeAdapterLoadStateEvent()
    object NowPlayingNotLoading : HomeAdapterLoadStateEvent()

    object PopularMoviesLoading : HomeAdapterLoadStateEvent()
    object PopularMoviesNotLoading : HomeAdapterLoadStateEvent()

    object PopularTvSeriesLoading : HomeAdapterLoadStateEvent()
    object PopularTvSeriesNotLoading : HomeAdapterLoadStateEvent()

    object TopRatedMoviesLoading : HomeAdapterLoadStateEvent()
    object TopRatedMoviesNotLoading : HomeAdapterLoadStateEvent()


    object TopRatedTvSeriesLoading : HomeAdapterLoadStateEvent()
    object TopRatedTvSeriesNotLoading : HomeAdapterLoadStateEvent()
}
