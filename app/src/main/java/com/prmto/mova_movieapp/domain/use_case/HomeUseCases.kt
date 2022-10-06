package com.prmto.mova_movieapp.domain.use_case

import com.prmto.mova_movieapp.domain.use_case.get_locale.GetLocaleUseCase
import com.prmto.mova_movieapp.domain.use_case.get_movie_genre_list.GetMovieGenreListUseCase
import com.prmto.mova_movieapp.domain.use_case.get_now_playing_movies.GetNowPlayingMoviesUseCase
import com.prmto.mova_movieapp.domain.use_case.get_tv_genre_list.GetTvGenreListUseCase

data class HomeUseCases(
    val getMovieGenreList: GetMovieGenreListUseCase,
    val getTvGenreList: GetTvGenreListUseCase,
    val getNowPlayingMoviesUseCase: GetNowPlayingMoviesUseCase,
    val getLocaleUseCase: GetLocaleUseCase,
)
