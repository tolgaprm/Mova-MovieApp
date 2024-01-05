package com.prmto.explore_domain.use_case

import com.prmto.core_domain.use_case.languageIsoCode.GetLanguageIsoCodeUseCase
import com.prmto.core_domain.use_case.movie.GetMovieGenreListUseCase
import com.prmto.core_domain.use_case.tv.GetTvGenreListUseCase
import com.prmto.explore_domain.use_case.movie.DiscoverMovieUseCase
import com.prmto.explore_domain.use_case.multisearch.MultiSearchUseCase
import com.prmto.explore_domain.use_case.tv.DiscoverTvUseCase

data class ExploreUseCases(
    val tvGenreListUseCase: GetTvGenreListUseCase,
    val movieGenreListUseCase: GetMovieGenreListUseCase,
    val getLanguageIsoCodeUseCase: GetLanguageIsoCodeUseCase,
    val discoverTvUseCase: DiscoverTvUseCase,
    val discoverMovieUseCase: DiscoverMovieUseCase,
    val multiSearchUseCase: MultiSearchUseCase
)