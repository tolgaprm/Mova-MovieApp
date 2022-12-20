package com.prmto.mova_movieapp.di

import com.prmto.mova_movieapp.core.domain.repository.DataStoreOperations
import com.prmto.mova_movieapp.core.domain.repository.RemoteRepository
import com.prmto.mova_movieapp.core.domain.use_case.GetLanguageIsoCodeUseCase
import com.prmto.mova_movieapp.core.domain.use_case.GetMovieGenreListUseCase
import com.prmto.mova_movieapp.core.domain.use_case.GetTvGenreListUseCase
import com.prmto.mova_movieapp.feature_explore.data.remote.ExploreApi
import com.prmto.mova_movieapp.feature_explore.data.repository.ExploreRepositoryImpl
import com.prmto.mova_movieapp.feature_explore.domain.repository.ExploreRepository
import com.prmto.mova_movieapp.feature_explore.domain.use_case.DiscoverMovieUseCase
import com.prmto.mova_movieapp.feature_explore.domain.use_case.DiscoverTvUseCase
import com.prmto.mova_movieapp.feature_explore.domain.use_case.ExploreUseCases
import com.prmto.mova_movieapp.feature_explore.domain.use_case.MultiSearchUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ExploreModule {

    @Provides
    @Singleton
    fun provideExploreApi(retrofit: Retrofit): ExploreApi {
        return retrofit.create(ExploreApi::class.java)
    }

    @Provides
    @Singleton
    fun provideExploreRepository(
        exploreApi: ExploreApi
    ): ExploreRepository {
        return ExploreRepositoryImpl(exploreApi)
    }

    @Provides
    @Singleton
    fun provideExploreUseCases(
        exploreRepository: ExploreRepository,
        remoteRepository: RemoteRepository,
        dataStoreOperations: DataStoreOperations
    ): ExploreUseCases {
        return ExploreUseCases(
            tvGenreListUseCase = GetTvGenreListUseCase(remoteRepository),
            movieGenreListUseCase = GetMovieGenreListUseCase(remoteRepository),
            getLanguageIsoCodeUseCase = GetLanguageIsoCodeUseCase(dataStoreOperations),
            discoverMovieUseCase = DiscoverMovieUseCase(exploreRepository,GetMovieGenreListUseCase(remoteRepository)),
            discoverTvUseCase = DiscoverTvUseCase(exploreRepository),
            multiSearchUseCase = MultiSearchUseCase(exploreRepository)
        )
    }
}