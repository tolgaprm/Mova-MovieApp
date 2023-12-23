package com.prmto.mova_movieapp.feature_home.domain.usecases

import com.prmto.mova_movieapp.core.domain.use_case.GetLanguageIsoCodeUseCase
import com.prmto.mova_movieapp.core.domain.use_case.UpdateLanguageIsoCodeUseCase
import com.prmto.mova_movieapp.feature_home.domain.movie.usecases.GetNowPlayingMoviesUseCase
import com.prmto.mova_movieapp.feature_home.domain.movie.usecases.GetPopularMoviesUseCase
import com.prmto.mova_movieapp.feature_home.domain.movie.usecases.GetTopRatedMoviesUseCase
import com.prmto.mova_movieapp.feature_home.domain.tv.usecases.GetPopularTvSeriesUseCase
import com.prmto.mova_movieapp.feature_home.domain.tv.usecases.GetTopRatedTvSeriesUseCase

data class HomeUseCases(
    val getNowPlayingMoviesUseCase: GetNowPlayingMoviesUseCase,
    val getLanguageIsoCodeUseCase: GetLanguageIsoCodeUseCase,
    val getPopularMoviesUseCase: GetPopularMoviesUseCase,
    val getTopRatedMoviesUseCase: GetTopRatedMoviesUseCase,
    val getPopularTvSeriesUseCase: GetPopularTvSeriesUseCase,
    val getTopRatedTvSeriesUseCase: GetTopRatedTvSeriesUseCase,
    val updateLanguageIsoCodeUseCase: UpdateLanguageIsoCodeUseCase
)