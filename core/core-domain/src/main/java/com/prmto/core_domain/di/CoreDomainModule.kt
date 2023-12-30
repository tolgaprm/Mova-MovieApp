package com.prmto.core_domain.di

import com.prmto.core_domain.repository.local.LocalDatabaseRepository
import com.prmto.core_domain.use_case.database.ClearAllDatabaseUseCase
import com.prmto.core_domain.use_case.database.LocalDatabaseUseCases
import com.prmto.core_domain.use_case.database.movie.GetFavoriteMovieIdsUseCase
import com.prmto.core_domain.use_case.database.movie.GetFavoriteMoviesUseCase
import com.prmto.core_domain.use_case.database.movie.ToggleMovieForFavoriteListUseCase
import com.prmto.core_domain.use_case.database.movie.ToggleMovieForWatchListUseCase
import com.prmto.core_domain.use_case.database.tv.GetFavoriteTvSeriesIdsUseCase
import com.prmto.core_domain.use_case.database.tv.GetFavoriteTvSeriesUseCase
import com.prmto.core_domain.use_case.database.tv.GetTvSeriesInWatchListUseCase
import com.prmto.core_domain.use_case.database.tv.GetTvSeriesWatchListItemIdsUseCase
import com.prmto.core_domain.use_case.database.tv.ToggleTvSeriesForFavoriteListUseCase
import com.prmto.core_domain.use_case.database.tv.ToggleTvSeriesForWatchListItemUseCase
import com.prmto.core_domain.use_case.movie.GetMovieWatchListItemIdsUseCase
import com.prmto.core_domain.use_case.movie.GetMoviesInWatchListUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CoreDomainModule {
    @Provides
    @Singleton
    fun provideLocalDatabaseUseCases(
        repository: LocalDatabaseRepository
    ): LocalDatabaseUseCases {
        return LocalDatabaseUseCases(
            clearAllDatabaseUseCase = ClearAllDatabaseUseCase(repository),
            toggleMovieForFavoriteListUseCase = ToggleMovieForFavoriteListUseCase(repository),
            toggleMovieForWatchListUseCase = ToggleMovieForWatchListUseCase(repository),
            getFavoriteMovieIdsUseCase = GetFavoriteMovieIdsUseCase(repository),
            getMovieWatchListItemIdsUseCase = GetMovieWatchListItemIdsUseCase(repository),
            toggleTvSeriesForFavoriteListUseCase = ToggleTvSeriesForFavoriteListUseCase(repository),
            toggleTvSeriesForWatchListItemUseCase = ToggleTvSeriesForWatchListItemUseCase(repository),
            getFavoriteTvSeriesIdsUseCase = GetFavoriteTvSeriesIdsUseCase(repository),
            getTvSeriesWatchListItemIdsUseCase = GetTvSeriesWatchListItemIdsUseCase(repository),
            getFavoriteMoviesUseCase = GetFavoriteMoviesUseCase(repository),
            getFavoriteTvSeriesUseCase = GetFavoriteTvSeriesUseCase(repository),
            getMoviesInWatchListUseCase = GetMoviesInWatchListUseCase(repository),
            getTvSeriesInWatchListUseCase = GetTvSeriesInWatchListUseCase(repository)
        )
    }
}