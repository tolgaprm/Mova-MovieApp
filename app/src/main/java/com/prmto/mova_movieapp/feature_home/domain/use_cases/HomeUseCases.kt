package com.prmto.mova_movieapp.feature_home.domain.use_cases

import com.prmto.mova_movieapp.core.domain.use_case.GetLanguageIsoCodeUseCase
import com.prmto.mova_movieapp.core.domain.use_case.UpdateLanguageIsoCodeUseCase

data class HomeUseCases(
    val getNowPlayingMoviesUseCase: GetNowPlayingMoviesUseCase,
    val getLanguageIsoCodeUseCase: GetLanguageIsoCodeUseCase,
    val getPopularMoviesUseCase: GetPopularMoviesUseCase,
    val getTopRatedMoviesUseCase: GetTopRatedMoviesUseCase,
    val getPopularTvSeriesUseCase: GetPopularTvSeriesUseCase,
    val getTopRatedTvSeriesUseCase: GetTopRatedTvSeriesUseCase,
    val updateLanguageIsoCodeUseCase: UpdateLanguageIsoCodeUseCase
)
