package com.prmto.home_domain.di

import com.prmto.core_domain.repository.DataStoreOperations
import com.prmto.core_domain.repository.GenreRepository
import com.prmto.core_domain.use_case.languageIsoCode.GetLanguageIsoCodeUseCase
import com.prmto.core_domain.use_case.languageIsoCode.UpdateLanguageIsoCodeUseCase
import com.prmto.core_domain.use_case.movie.GetMovieGenreListUseCase
import com.prmto.core_domain.use_case.tv.GetTvGenreListUseCase
import com.prmto.home_domain.repository.movie.HomeMovieRepository
import com.prmto.home_domain.repository.tv.HomeTvRepository
import com.prmto.home_domain.usecases.HomeUseCases
import com.prmto.home_domain.usecases.movie.GetNowPlayingMoviesUseCase
import com.prmto.home_domain.usecases.movie.GetPopularMoviesUseCase
import com.prmto.home_domain.usecases.movie.GetTopRatedMoviesUseCase
import com.prmto.home_domain.usecases.tv.GetPopularTvSeriesUseCase
import com.prmto.home_domain.usecases.tv.GetTopRatedTvSeriesUseCase
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