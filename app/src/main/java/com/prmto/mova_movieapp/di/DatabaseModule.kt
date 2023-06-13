package com.prmto.mova_movieapp.di

import android.content.Context
import androidx.room.Room
import com.prmto.mova_movieapp.core.data.data_source.local.MovaDatabase
import com.prmto.mova_movieapp.core.data.data_source.local.migration.Migration_1_2
import com.prmto.mova_movieapp.core.data.repository.local.LocalDatabaseRepositoryImpl
import com.prmto.mova_movieapp.core.data.repository.local.MovieLocalRepositoryImpl
import com.prmto.mova_movieapp.core.data.repository.local.TvSeriesLocalRepositoryImpl
import com.prmto.mova_movieapp.core.domain.repository.local.LocalDatabaseRepository
import com.prmto.mova_movieapp.core.domain.repository.local.MovieLocalRepository
import com.prmto.mova_movieapp.core.domain.repository.local.TvSeriesLocalRepository
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
        @ApplicationContext context: Context,
    ): MovaDatabase {
        return Room.databaseBuilder(
            context = context,
            klass = MovaDatabase::class.java,
            "MovaDatabase"
        ).addMigrations(
            Migration_1_2
        ).build()
    }

    @Provides
    @Singleton
    fun provideMovieLocalRepository(
        movaDatabase: MovaDatabase,
    ): MovieLocalRepository {
        return MovieLocalRepositoryImpl(movaDatabase)
    }

    @Provides
    @Singleton
    fun provideTvSeriesLocalRepository(
        movaDatabase: MovaDatabase,
    ): TvSeriesLocalRepository {
        return TvSeriesLocalRepositoryImpl(movaDatabase)
    }

    @Provides
    @Singleton
    fun provideLocalDatabaseRepository(
        database: MovaDatabase,
        movieLocalRepository: MovieLocalRepository,
        tvSeriesLocalRepository: TvSeriesLocalRepository,
    ): LocalDatabaseRepository {
        return LocalDatabaseRepositoryImpl(
            database = database,
            movieLocalRepository = movieLocalRepository,
            tvSeriesLocalRepository = tvSeriesLocalRepository
        )
    }

    @Provides
    @Singleton
    fun provideLocalDatabaseUseCases(
        repository: LocalDatabaseRepository,
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