package com.prmto.mova_movieapp.feature_movie_tv_detail.domain.use_cases

data class DetailUseCases(
    val movieDetailUseCase: GetMovieDetailUseCase,
    val tvDetailUseCase: GetTvDetailUseCase,
    val getMovieRecommendationUseCase: GetMovieRecommendationUseCase,
    val getTvRecommendationUseCase: GetTvRecommendationUseCase,
    val getMovieVideosUseCase: GetMovieVideosUseCase,
    val getTvVideosUseCase: GetTvVideosUseCase
)
