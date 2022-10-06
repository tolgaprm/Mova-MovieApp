package com.prmto.mova_movieapp.di

import android.content.Context
import com.prmto.mova_movieapp.data.remote.TMDBApi
import com.prmto.mova_movieapp.data.repository.DataOperationsImpl
import com.prmto.mova_movieapp.data.repository.RemoteRepositoryImpl
import com.prmto.mova_movieapp.domain.repository.DataStoreOperations
import com.prmto.mova_movieapp.domain.repository.RemoteRepository
import com.prmto.mova_movieapp.domain.use_case.HomeUseCases
import com.prmto.mova_movieapp.domain.use_case.get_locale.GetLocaleUseCase
import com.prmto.mova_movieapp.domain.use_case.get_movie_genre_list.GetMovieGenreListUseCase
import com.prmto.mova_movieapp.domain.use_case.get_now_playing_movies.GetNowPlayingMoviesUseCase
import com.prmto.mova_movieapp.domain.use_case.get_tv_genre_list.GetTvGenreListUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideRemoteRepository(tmdbApi: TMDBApi): RemoteRepository {
        return RemoteRepositoryImpl(tmdbApi = tmdbApi)
    }

    @Provides
    @Singleton
    fun provideHomeUseCases(
        remoteRepository: RemoteRepository,
        dataStoreOperations: DataStoreOperations
    ): HomeUseCases {
        return HomeUseCases(
            getMovieGenreList = GetMovieGenreListUseCase(remoteRepository),
            getTvGenreList = GetTvGenreListUseCase(remoteRepository),
            getNowPlayingMoviesUseCase = GetNowPlayingMoviesUseCase(remoteRepository),
            getLocaleUseCase = GetLocaleUseCase(dataStoreOperations)
        )
    }

    @Provides
    @Singleton
    fun provideDataStoreOperations(
        @ApplicationContext context: Context
    ): DataStoreOperations {
        return DataOperationsImpl(context = context)
    }

}