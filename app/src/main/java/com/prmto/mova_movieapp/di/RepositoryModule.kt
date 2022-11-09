package com.prmto.mova_movieapp.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.prmto.mova_movieapp.data.remote.TMDBApi
import com.prmto.mova_movieapp.data.repository.DataOperationsImpl
import com.prmto.mova_movieapp.data.repository.NetworkConnectivityObserver
import com.prmto.mova_movieapp.data.repository.RemoteRepositoryImpl
import com.prmto.mova_movieapp.domain.repository.ConnectivityObserver
import com.prmto.mova_movieapp.domain.repository.DataStoreOperations
import com.prmto.mova_movieapp.domain.repository.RemoteRepository
import com.prmto.mova_movieapp.domain.use_case.ExploreUseCases
import com.prmto.mova_movieapp.domain.use_case.HomeUseCases
import com.prmto.mova_movieapp.domain.use_case.SettingUseCase
import com.prmto.mova_movieapp.domain.use_case.discover_movie.DiscoverMovieUseCase
import com.prmto.mova_movieapp.domain.use_case.discover_tv.DiscoverTvUseCase
import com.prmto.mova_movieapp.domain.use_case.get_language_iso_code.GetLanguageIsoCodeUseCase
import com.prmto.mova_movieapp.domain.use_case.get_movie_genre_list.GetMovieGenreListUseCase
import com.prmto.mova_movieapp.domain.use_case.get_now_playing_movies.GetNowPlayingMoviesUseCase
import com.prmto.mova_movieapp.domain.use_case.get_popular_movies.GetPopularMoviesUseCase
import com.prmto.mova_movieapp.domain.use_case.get_popular_tv_series.GetPopularTvSeries
import com.prmto.mova_movieapp.domain.use_case.get_top_rated_movies.GetTopRatedMoviesUseCase
import com.prmto.mova_movieapp.domain.use_case.get_top_rated_tv_series.GetTopRatedTvSeriesUseCase
import com.prmto.mova_movieapp.domain.use_case.get_tv_genre_list.GetTvGenreListUseCase
import com.prmto.mova_movieapp.domain.use_case.get_ui_mode.GetUIModeUseCase
import com.prmto.mova_movieapp.domain.use_case.update_current_language_iso_code.UpdateLanguageIsoCodeUseCase
import com.prmto.mova_movieapp.domain.use_case.update_ui_mode.UpdateUIModeUseCase
import com.prmto.mova_movieapp.util.DefaultDispatchers
import com.prmto.mova_movieapp.util.DispatchersProvider
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
            getLanguageIsoCodeUseCase = GetLanguageIsoCodeUseCase(dataStoreOperations),
            getPopularMoviesUseCase = GetPopularMoviesUseCase(remoteRepository),
            getTopRatedMoviesUseCase = GetTopRatedMoviesUseCase(remoteRepository),
            getPopularTvSeries = GetPopularTvSeries(remoteRepository),
            getTopRatedTvSeriesUseCase = GetTopRatedTvSeriesUseCase(remoteRepository),
            updateLanguageIsoCodeUseCase = UpdateLanguageIsoCodeUseCase(dataStoreOperations)
        )
    }

    @Provides
    @Singleton
    fun provideExploreUseCases(
        remoteRepository: RemoteRepository,
        dataStoreOperations: DataStoreOperations
    ): ExploreUseCases {
        return ExploreUseCases(
            tvGenreListUseCase = GetTvGenreListUseCase(remoteRepository),
            movieGenreListUseCase = GetMovieGenreListUseCase(remoteRepository),
            getLanguageIsoCodeUseCase = GetLanguageIsoCodeUseCase(dataStoreOperations),
            discoverMovieUseCase = DiscoverMovieUseCase(remoteRepository),
            discoverTvUseCase = DiscoverTvUseCase(remoteRepository)
        )
    }

    @Provides
    @Singleton
    fun provideSettingsUseCases(
        dataStoreOperations: DataStoreOperations
    ): SettingUseCase {
        return SettingUseCase(
            getUIModeUseCase = GetUIModeUseCase(dataStoreOperations),
            updateUIModeUseCase = UpdateUIModeUseCase(dataStoreOperations),
            updateLanguageIsoCodeUseCase = UpdateLanguageIsoCodeUseCase(dataStoreOperations),
            getLanguageIsoCodeUseCase = GetLanguageIsoCodeUseCase(dataStoreOperations)
        )
    }

    @Provides
    @Singleton
    fun provideDataStoreOperations(
        dataStore: DataStore<Preferences>
    ): DataStoreOperations {
        return DataOperationsImpl(dataStore = dataStore)
    }

    @Provides
    @Singleton
    fun provideConnectivityManager(
        @ApplicationContext context: Context
    ): ConnectivityObserver {
        return NetworkConnectivityObserver(context)
    }


    @Provides
    @Singleton
    fun provideDispatchers(): DispatchersProvider =
        DefaultDispatchers()

}