package com.prmto.mova_movieapp.feature_movie_tv_detail.domain.di

import com.prmto.mova_movieapp.core.domain.use_case.database.movie.GetFavoriteMovieIdsUseCase
import com.prmto.mova_movieapp.core.domain.use_case.database.tv.GetFavoriteTvSeriesIdsUseCase
import com.prmto.mova_movieapp.core.domain.use_case.database.tv.GetTvSeriesWatchListItemIdsUseCase
import com.prmto.mova_movieapp.core.domain.use_case.movie.GetMovieGenreListUseCase
import com.prmto.mova_movieapp.core.domain.use_case.movie.GetMovieWatchListItemIdsUseCase
import com.prmto.mova_movieapp.core.domain.use_case.tv.GetTvGenreListUseCase
import com.prmto.mova_movieapp.core.presentation.countryCode.CountryCodeProvider
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
        countryCodeProvider: CountryCodeProvider,
        getFavoriteMovieIdsUseCase: GetFavoriteMovieIdsUseCase,
        getWatchListMovieIdsUseCase: GetMovieWatchListItemIdsUseCase,
        getFavoriteTvSeriesIdsUseCase: GetFavoriteTvSeriesIdsUseCase,
        getTvSeriesWatchListItemIdsUseCase: GetTvSeriesWatchListItemIdsUseCase
    ): DetailUseCases {
        return DetailUseCases(
            movieDetailUseCase = GetMovieDetailUseCase(
                movieDetailRepository = movieDetailRepository,
                countryCodeProvider = countryCodeProvider,
                getFavoriteMovieIdsUseCase = getFavoriteMovieIdsUseCase,
                getWatchListMovieIdsUseCase = getWatchListMovieIdsUseCase
            ),
            tvDetailUseCase = GetTvDetailUseCase(
                tvDetailRepository = tvDetailRepository,
                countryCodeProvider = countryCodeProvider,
                getFavoriteTvSeriesIdsUseCase = getFavoriteTvSeriesIdsUseCase,
                getTvSeriesWatchListItemIdsUseCase = getTvSeriesWatchListItemIdsUseCase
            ),
            getMovieRecommendationUseCase = GetMovieRecommendationUseCase(
                movieDetailRepository = movieDetailRepository,
                getMovieGenreListUseCase = getMovieGenreListUseCase
            ),
            getTvRecommendationUseCase = GetTvRecommendationUseCase(
                tvDetailRepository = tvDetailRepository,
                getTvGenreListUseCase = getTvGenreListUseCase
            ),
            getMovieVideosUseCase = GetMovieVideosUseCase(movieDetailRepository = movieDetailRepository),
            getTvVideosUseCase = GetTvVideosUseCase(tvDetailRepository = tvDetailRepository)
        )
    }
}