package com.prmto.mova_movieapp.domain.use_case

import com.prmto.mova_movieapp.domain.use_case.get_movie_genre_list.GetMovieGenreList
import com.prmto.mova_movieapp.domain.use_case.get_tv_genre_list.GetTvGenreList

data class UseCases(
    val getMovieGenreList: GetMovieGenreList,
    val getTvGenreList: GetTvGenreList
)
