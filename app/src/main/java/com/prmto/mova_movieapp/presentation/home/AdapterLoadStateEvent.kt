package com.prmto.mova_movieapp.presentation.home

import com.prmto.mova_movieapp.presentation.util.UiText

sealed class AdapterLoadStateEvent {
    object NowPlayingLoading : AdapterLoadStateEvent()
    object NowPlayingNotLoading : AdapterLoadStateEvent()
    data class NowPlayingError(val uiText: UiText) : AdapterLoadStateEvent()

    object PopularMoviesLoading : AdapterLoadStateEvent()
    object PopularMoviesNotLoading : AdapterLoadStateEvent()
    data class PopularMoviesError(val uiText: UiText) : AdapterLoadStateEvent()

    object PopularTvSeriesLoading : AdapterLoadStateEvent()
    object PopularTvSeriesNotLoading : AdapterLoadStateEvent()
    data class PopularTvSeriesError(val uiText: UiText) : AdapterLoadStateEvent()

    object TopRatedMoviesLoading : AdapterLoadStateEvent()
    object TopRatedMoviesNotLoading : AdapterLoadStateEvent()
    data class TopRatedMoviesError(val uiText: UiText) : AdapterLoadStateEvent()


    object TopRatedTvSeriesLoading : AdapterLoadStateEvent()
    object TopRatedTvSeriesNotLoading : AdapterLoadStateEvent()
    data class TopRatedTvSeriesError(val uiText: UiText) : AdapterLoadStateEvent()
}
