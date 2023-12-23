package com.prmto.mova_movieapp.feature_home.domain.di

import com.prmto.mova_movieapp.core.domain.repository.DataStoreOperations
import com.prmto.mova_movieapp.core.domain.repository.RemoteRepository
import com.prmto.mova_movieapp.core.domain.use_case.GetLanguageIsoCodeUseCase
import com.prmto.mova_movieapp.core.domain.use_case.GetMovieGenreListUseCase
import com.prmto.mova_movieapp.core.domain.use_case.GetTvGenreListUseCase
import com.prmto.mova_movieapp.core.domain.use_case.UpdateLanguageIsoCodeUseCase
import com.prmto.mova_movieapp.feature_home.domain.movie.HomeMovieRepository
import com.prmto.mova_movieapp.feature_home.domain.movie.usecases.GetNowPlayingMoviesUseCase
import com.prmto.mova_movieapp.feature_home.domain.movie.usecases.GetPopularMoviesUseCase
import com.prmto.mova_movieapp.feature_home.domain.movie.usecases.GetTopRatedMoviesUseCase
import com.prmto.mova_movieapp.feature_home.domain.tv.HomeTvRepository
import com.prmto.mova_movieapp.feature_home.domain.tv.usecases.GetPopularTvSeriesUseCase
import com.prmto.mova_movieapp.feature_home.domain.tv.usecases.GetTopRatedTvSeriesUseCase
import com.prmto.mova_movieapp.feature_home.domain.usecases.HomeUseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object HomeDomainModule {

    @Provides
    @ViewModelScoped
    fun provideHomeUseCases(
        homeMovieRepository: HomeMovieRepository,
        homeTvRepository: HomeTvRepository,
        dataStoreOperations: DataStoreOperations,
        remoteRepository: RemoteRepository
    ): HomeUseCases {
        return HomeUseCases(
            getNowPlayingMoviesUseCase = GetNowPlayingMoviesUseCase(
                homeMovieRepository,
                GetMovieGenreListUseCase(remoteRepository)
            ),
            getLanguageIsoCodeUseCase = GetLanguageIsoCodeUseCase(dataStoreOperations),
            getPopularMoviesUseCase = GetPopularMoviesUseCase(
                homeMovieRepository,
                GetMovieGenreListUseCase(remoteRepository)
            ),
            getTopRatedMoviesUseCase = GetTopRatedMoviesUseCase(
                homeMovieRepository,
                GetMovieGenreListUseCase(remoteRepository)
            ),
            getPopularTvSeriesUseCase = GetPopularTvSeriesUseCase(
                homeTvRepository,
                GetTvGenreListUseCase(remoteRepository)
            ),
            getTopRatedTvSeriesUseCase = GetTopRatedTvSeriesUseCase(
                homeTvRepository,
                GetTvGenreListUseCase(remoteRepository)
            ),
            updateLanguageIsoCodeUseCase = UpdateLanguageIsoCodeUseCase(dataStoreOperations)
        )
    }
}