package com.prmto.mova_movieapp.feature_movie_tv_detail.domain.di

import com.prmto.mova_movieapp.core.domain.countryCode.CountryCodeProvider
import com.prmto.mova_movieapp.core.domain.use_case.movie.GetMovieGenreListUseCase
import com.prmto.mova_movieapp.core.domain.use_case.tv.GetTvGenreListUseCase
import com.prmto.mova_movieapp.feature_movie_tv_detail.domain.repository.movie.MovieDetailRepository
import com.prmto.mova_movieapp.feature_movie_tv_detail.domain.repository.tv.TvDetailRepository
import com.prmto.mova_movieapp.feature_movie_tv_detail.domain.use_cases.DetailUseCases
import com.prmto.mova_movieapp.feature_movie_tv_detail.domain.use_cases.movie.usecases.GetMovieDetailUseCase
import com.prmto.mova_movieapp.feature_movie_tv_detail.domain.use_cases.movie.usecases.GetMovieRecommendationUseCase
import com.prmto.mova_movieapp.feature_movie_tv_detail.domain.use_cases.movie.usecases.GetMovieVideosUseCase
import com.prmto.mova_movieapp.feature_movie_tv_detail.domain.use_cases.tv.GetTvDetailUseCase
import com.prmto.mova_movieapp.feature_movie_tv_detail.domain.use_cases.tv.GetTvRecommendationUseCase
import com.prmto.mova_movieapp.feature_movie_tv_detail.domain.use_cases.tv.GetTvVideosUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object DetailDomainModule {

    @Provides
    @ViewModelScoped
    fun provideDetailUseCases(
        movieDetailRepository: MovieDetailRepository,
        tvDetailRepository: TvDetailRepository,
        getMovieGenreListUseCase: GetMovieGenreListUseCase,
        getTvGenreListUseCase: GetTvGenreListUseCase,
        countryCodeProvider: CountryCodeProvider
    ): DetailUseCases {
        return DetailUseCases(
            movieDetailUseCase = GetMovieDetailUseCase(
                movieDetailRepository,
                countryCodeProvider
            ),
            tvDetailUseCase = GetTvDetailUseCase(
                tvDetailRepository,
                countryCodeProvider
            ),
            getMovieRecommendationUseCase = GetMovieRecommendationUseCase(
                movieDetailRepository,
                getMovieGenreListUseCase
            ),
            getTvRecommendationUseCase = GetTvRecommendationUseCase(
                tvDetailRepository,
                getTvGenreListUseCase
            ),
            getMovieVideosUseCase = GetMovieVideosUseCase(movieDetailRepository),
            getTvVideosUseCase = GetTvVideosUseCase(tvDetailRepository)
        )
    }
}