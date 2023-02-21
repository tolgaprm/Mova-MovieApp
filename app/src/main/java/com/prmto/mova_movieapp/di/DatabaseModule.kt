package com.prmto.mova_movieapp.di

import android.content.Context
import androidx.room.Room
import com.prmto.mova_movieapp.core.data.data_source.local.MovaDatabase
import com.prmto.mova_movieapp.core.data.repository.LocalDatabaseRepositoryImpl
import com.prmto.mova_movieapp.core.domain.repository.LocalDatabaseRepository
import com.prmto.mova_movieapp.core.domain.use_case.*
import com.prmto.mova_movieapp.core.domain.use_case.database.*
import com.prmto.mova_movieapp.core.domain.use_case.database.movie.GetFavoriteMovieIdsUseCase
import com.prmto.mova_movieapp.core.domain.use_case.database.movie.GetFavoriteMoviesUseCase
import com.prmto.mova_movieapp.core.domain.use_case.database.movie.ToggleMovieForFavoriteListUseCase
import com.prmto.mova_movieapp.core.domain.use_case.database.movie.ToggleMovieForWatchListUseCase
import com.prmto.mova_movieapp.core.domain.use_case.database.tv.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideMovaDatabase(
        @ApplicationContext context: Context
    ): MovaDatabase {
        return Room.databaseBuilder(
            context = context,
            klass = MovaDatabase::class.java,
            "MovaDatabase"
        ).build()
    }

    @Provides
    @Singleton
    fun provideLocalDatabaseRepository(
        database: MovaDatabase
    ): LocalDatabaseRepository {
        return LocalDatabaseRepositoryImpl(database = database)
    }

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