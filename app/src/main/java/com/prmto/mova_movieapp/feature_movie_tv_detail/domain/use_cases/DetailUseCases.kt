package com.prmto.mova_movieapp.feature_movie_tv_detail.domain.use_cases

import com.prmto.mova_movieapp.feature_movie_tv_detail.domain.movie.usecases.GetMovieDetailUseCase
import com.prmto.mova_movieapp.feature_movie_tv_detail.domain.movie.usecases.GetMovieRecommendationUseCase
import com.prmto.mova_movieapp.feature_movie_tv_detail.domain.movie.usecases.GetMovieVideosUseCase
import com.prmto.mova_movieapp.feature_movie_tv_detail.domain.tv.usecases.GetTvDetailUseCase
import com.prmto.mova_movieapp.feature_movie_tv_detail.domain.tv.usecases.GetTvRecommendationUseCase
import com.prmto.mova_movieapp.feature_movie_tv_detail.domain.tv.usecases.GetTvVideosUseCase

data class DetailUseCases(
    val movieDetailUseCase: GetMovieDetailUseCase,
    val tvDetailUseCase: GetTvDetailUseCase,
    val getMovieRecommendationUseCase: GetMovieRecommendationUseCase,
    val getTvRecommendationUseCase: GetTvRecommendationUseCase,
    val getMovieVideosUseCase: GetMovieVideosUseCase,
    val getTvVideosUseCase: GetTvVideosUseCase
)