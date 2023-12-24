package com.prmto.mova_movieapp.feature_explore.domain.di

import com.prmto.mova_movieapp.core.domain.repository.DataStoreOperations
import com.prmto.mova_movieapp.core.domain.repository.RemoteRepository
import com.prmto.mova_movieapp.core.domain.use_case.GetLanguageIsoCodeUseCase
import com.prmto.mova_movieapp.core.domain.use_case.GetMovieGenreListUseCase
import com.prmto.mova_movieapp.core.domain.use_case.GetTvGenreListUseCase
import com.prmto.mova_movieapp.feature_explore.domain.repository.ExploreRepository
import com.prmto.mova_movieapp.feature_explore.domain.use_case.DiscoverMovieUseCase
import com.prmto.mova_movieapp.feature_explore.domain.use_case.DiscoverTvUseCase
import com.prmto.mova_movieapp.feature_explore.domain.use_case.ExploreUseCases
import com.prmto.mova_movieapp.feature_explore.domain.use_case.MultiSearchUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object ExploreDomainModule {

    @Provides
    @ViewModelScoped
    fun provideExploreUseCases(
        exploreRepository: ExploreRepository,
        remoteRepository: RemoteRepository,
        dataStoreOperations: DataStoreOperations
    ): ExploreUseCases {
        return ExploreUseCases(
            tvGenreListUseCase = GetTvGenreListUseCase(remoteRepository),
            movieGenreListUseCase = GetMovieGenreListUseCase(remoteRepository),
            getLanguageIsoCodeUseCase = GetLanguageIsoCodeUseCase(dataStoreOperations),
            discoverMovieUseCase = DiscoverMovieUseCase(
                exploreRepository,
                GetMovieGenreListUseCase(remoteRepository)
            ),
            discoverTvUseCase = DiscoverTvUseCase(
                exploreRepository,
                GetTvGenreListUseCase(remoteRepository)
            ),
            multiSearchUseCase = MultiSearchUseCase(
                exploreRepository,
                GetMovieGenreListUseCase(remoteRepository),
                GetTvGenreListUseCase(remoteRepository)
            )
        )
    }
}