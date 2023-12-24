package com.prmto.mova_movieapp.feature_explore.domain.use_case

import com.prmto.mova_movieapp.core.domain.use_case.languageIsoCode.GetLanguageIsoCodeUseCase
import com.prmto.mova_movieapp.core.domain.use_case.movie.GetMovieGenreListUseCase
import com.prmto.mova_movieapp.core.domain.use_case.tv.GetTvGenreListUseCase
import com.prmto.mova_movieapp.feature_explore.domain.use_case.movie.DiscoverMovieUseCase
import com.prmto.mova_movieapp.feature_explore.domain.use_case.multisearch.MultiSearchUseCase
import com.prmto.mova_movieapp.feature_explore.domain.use_case.tv.DiscoverTvUseCase

data class ExploreUseCases(
    val tvGenreListUseCase: GetTvGenreListUseCase,
    val movieGenreListUseCase: GetMovieGenreListUseCase,
    val getLanguageIsoCodeUseCase: GetLanguageIsoCodeUseCase,
    val discoverTvUseCase: DiscoverTvUseCase,
    val discoverMovieUseCase: DiscoverMovieUseCase,
    val multiSearchUseCase: MultiSearchUseCase
)