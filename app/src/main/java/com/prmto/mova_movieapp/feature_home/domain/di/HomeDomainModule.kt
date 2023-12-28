package com.prmto.mova_movieapp.feature_home.domain.di

import com.prmto.mova_movieapp.core.domain.repository.DataStoreOperations
import com.prmto.mova_movieapp.core.domain.repository.GenreRepository
import com.prmto.mova_movieapp.core.domain.use_case.languageIsoCode.GetLanguageIsoCodeUseCase
import com.prmto.mova_movieapp.core.domain.use_case.languageIsoCode.UpdateLanguageIsoCodeUseCase
import com.prmto.mova_movieapp.core.domain.use_case.movie.GetMovieGenreListUseCase
import com.prmto.mova_movieapp.core.domain.use_case.tv.GetTvGenreListUseCase
import com.prmto.mova_movieapp.feature_home.domain.repository.movie.HomeMovieRepository
import com.prmto.mova_movieapp.feature_home.domain.repository.tv.HomeTvRepository
import com.prmto.mova_movieapp.feature_home.domain.usecases.HomeUseCases
import com.prmto.mova_movieapp.feature_home.domain.usecases.movie.GetNowPlayingMoviesUseCase
import com.prmto.mova_movieapp.feature_home.domain.usecases.movie.GetPopularMoviesUseCase
import com.prmto.mova_movieapp.feature_home.domain.usecases.movie.GetTopRatedMoviesUseCase
import com.prmto.mova_movieapp.feature_home.domain.usecases.tv.GetPopularTvSeriesUseCase
import com.prmto.mova_movieapp.feature_home.domain.usecases.tv.GetTopRatedTvSeriesUseCase
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
        genreRepository: GenreRepository
    ): HomeUseCases {
        return HomeUseCases(
            getNowPlayingMoviesUseCase = GetNowPlayingMoviesUseCase(
                homeMovieRepository = homeMovieRepository,
                getMovieGenreListUseCase = GetMovieGenreListUseCase(
                    genreRepository
                )
            ),
            getLanguageIsoCodeUseCase = GetLanguageIsoCodeUseCase(dataStoreOperations),
            getPopularMoviesUseCase = GetPopularMoviesUseCase(
                homeMovieRepository = homeMovieRepository,
                getMovieGenreListUseCase = GetMovieGenreListUseCase(genreRepository),
            ),
            getTopRatedMoviesUseCase = GetTopRatedMoviesUseCase(
                homeMovieRepository,
                GetMovieGenreListUseCase(genreRepository),
            ),
            getPopularTvSeriesUseCase = GetPopularTvSeriesUseCase(
                homeTvRepository = homeTvRepository,
                getTvGenreListUseCase = GetTvGenreListUseCase(genreRepository)
            ),
            getTopRatedTvSeriesUseCase = GetTopRatedTvSeriesUseCase(
                homeTvRepository,
                GetTvGenreListUseCase(genreRepository),
            ),
            updateLanguageIsoCodeUseCase = UpdateLanguageIsoCodeUseCase(dataStoreOperations)
        )
    }
}