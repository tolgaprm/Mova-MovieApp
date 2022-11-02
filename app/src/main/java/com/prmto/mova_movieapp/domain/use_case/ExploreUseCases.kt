package com.prmto.mova_movieapp.domain.use_case

import com.prmto.mova_movieapp.domain.use_case.discover_movie.DiscoverMovieUseCase
import com.prmto.mova_movieapp.domain.use_case.discover_tv.DiscoverTvUseCase
import com.prmto.mova_movieapp.domain.use_case.get_locale.GetLocaleUseCase
import com.prmto.mova_movieapp.domain.use_case.get_movie_genre_list.GetMovieGenreListUseCase
import com.prmto.mova_movieapp.domain.use_case.get_tv_genre_list.GetTvGenreListUseCase

data class ExploreUseCases(
    val tvGenreListUseCase: GetTvGenreListUseCase,
    val movieGenreListUseCase: GetMovieGenreListUseCase,
    val getLocaleUseCase: GetLocaleUseCase,
    val discoverTvUseCase: DiscoverTvUseCase,
    val discoverMovieUseCase: DiscoverMovieUseCase
)