package com.prmto.explore_domain.di

import com.prmto.core_domain.repository.DataStoreOperations
import com.prmto.core_domain.repository.GenreRepository
import com.prmto.core_domain.use_case.languageIsoCode.GetLanguageIsoCodeUseCase
import com.prmto.core_domain.use_case.movie.GetMovieGenreListUseCase
import com.prmto.core_domain.use_case.tv.GetTvGenreListUseCase
import com.prmto.explore_domain.use_case.ExploreUseCases
import com.prmto.explore_domain.use_case.movie.DiscoverMovieUseCase
import com.prmto.explore_domain.use_case.multisearch.MultiSearchUseCase
import com.prmto.explore_domain.use_case.tv.DiscoverTvUseCase
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
        exploreRepository: com.prmto.explore_domain.repository.ExploreRepository,
        genreRepository: GenreRepository,
        dataStoreOperations: DataStoreOperations
    ): ExploreUseCases {
        return ExploreUseCases(
            tvGenreListUseCase = GetTvGenreListUseCase(genreRepository),
            movieGenreListUseCase = GetMovieGenreListUseCase(genreRepository),
            getLanguageIsoCodeUseCase = GetLanguageIsoCodeUseCase(dataStoreOperations),
            discoverMovieUseCase = DiscoverMovieUseCase(
                exploreRepository = exploreRepository,
                getMovieGenreListUseCase = GetMovieGenreListUseCase(genreRepository)
            ),
            discoverTvUseCase = DiscoverTvUseCase(
                exploreRepository,
                GetTvGenreListUseCase(genreRepository)
            ),
            multiSearchUseCase = MultiSearchUseCase(
                exploreRepository
            )
        )
    }
}