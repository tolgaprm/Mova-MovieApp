package com.prmto.domain.di

import com.prmto.core_domain.countryCode.CountryCodeProvider
import com.prmto.core_domain.use_case.database.movie.GetFavoriteMovieIdsUseCase
import com.prmto.core_domain.use_case.database.tv.GetFavoriteTvSeriesIdsUseCase
import com.prmto.core_domain.use_case.database.tv.GetTvSeriesWatchListItemIdsUseCase
import com.prmto.core_domain.use_case.movie.GetMovieGenreListUseCase
import com.prmto.core_domain.use_case.movie.GetMovieWatchListItemIdsUseCase
import com.prmto.core_domain.use_case.tv.GetTvGenreListUseCase
import com.prmto.domain.repository.movie.MovieDetailRepository
import com.prmto.domain.repository.tv.TvDetailRepository
import com.prmto.domain.use_cases.DetailUseCases
import com.prmto.domain.use_cases.movie.usecases.GetMovieDetailUseCase
import com.prmto.domain.use_cases.movie.usecases.GetMovieRecommendationUseCase
import com.prmto.domain.use_cases.movie.usecases.GetMovieVideosUseCase
import com.prmto.domain.use_cases.tv.GetTvDetailUseCase
import com.prmto.domain.use_cases.tv.GetTvRecommendationUseCase
import com.prmto.domain.use_cases.tv.GetTvVideosUseCase
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