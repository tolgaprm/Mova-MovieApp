package com.prmto.mova_movieapp.domain.use_case

import com.prmto.mova_movieapp.domain.use_case.get_movie_detail.GetMovieDetailUseCase
import com.prmto.mova_movieapp.domain.use_case.get_tv_detail.GetTvDetailUseCase

data class DetailUseCases(
    val movieDetailUseCase: GetMovieDetailUseCase,
    val tvDetailUseCase: GetTvDetailUseCase
)
