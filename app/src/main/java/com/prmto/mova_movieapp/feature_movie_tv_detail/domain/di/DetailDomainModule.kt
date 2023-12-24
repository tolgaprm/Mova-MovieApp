package com.prmto.mova_movieapp.feature_movie_tv_detail.domain.di

import com.prmto.mova_movieapp.core.domain.use_case.GetMovieGenreListUseCase
import com.prmto.mova_movieapp.core.domain.use_case.GetTvGenreListUseCase
import com.prmto.mova_movieapp.core.util.countryCode.CountryCodeProvider
import com.prmto.mova_movieapp.feature_movie_tv_detail.domain.movie.repository.MovieDetailRepository
import com.prmto.mova_movieapp.feature_movie_tv_detail.domain.movie.usecases.GetMovieDetailUseCase
import com.prmto.mova_movieapp.feature_movie_tv_detail.domain.movie.usecases.GetMovieRecommendationUseCase
import com.prmto.mova_movieapp.feature_movie_tv_detail.domain.movie.usecases.GetMovieVideosUseCase
import com.prmto.mova_movieapp.feature_movie_tv_detail.domain.tv.repository.TvDetailRepository
import com.prmto.mova_movieapp.feature_movie_tv_detail.domain.tv.usecases.GetTvDetailUseCase
import com.prmto.mova_movieapp.feature_movie_tv_detail.domain.tv.usecases.GetTvRecommendationUseCase
import com.prmto.mova_movieapp.feature_movie_tv_detail.domain.tv.usecases.GetTvVideosUseCase
import com.prmto.mova_movieapp.feature_movie_tv_detail.domain.use_cases.DetailUseCases
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