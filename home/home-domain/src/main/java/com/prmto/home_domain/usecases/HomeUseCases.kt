package com.prmto.home_domain.usecases

import com.prmto.core_domain.use_case.languageIsoCode.GetLanguageIsoCodeUseCase
import com.prmto.core_domain.use_case.languageIsoCode.UpdateLanguageIsoCodeUseCase
import com.prmto.home_domain.usecases.movie.GetNowPlayingMoviesUseCase
import com.prmto.home_domain.usecases.movie.GetPopularMoviesUseCase
import com.prmto.home_domain.usecases.movie.GetTopRatedMoviesUseCase
import com.prmto.home_domain.usecases.tv.GetPopularTvSeriesUseCase
import com.prmto.home_domain.usecases.tv.GetTopRatedTvSeriesUseCase

data class HomeUseCases(
    val getNowPlayingMoviesUseCase: GetNowPlayingMoviesUseCase,
    val getLanguageIsoCodeUseCase: GetLanguageIsoCodeUseCase,
    val getPopularMoviesUseCase: GetPopularMoviesUseCase,
    val getTopRatedMoviesUseCase: GetTopRatedMoviesUseCase,
    val getPopularTvSeriesUseCase: GetPopularTvSeriesUseCase,
    val getTopRatedTvSeriesUseCase: GetTopRatedTvSeriesUseCase,
    val updateLanguageIsoCodeUseCase: UpdateLanguageIsoCodeUseCase
)