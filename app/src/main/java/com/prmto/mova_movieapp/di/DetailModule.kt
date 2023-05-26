package com.prmto.mova_movieapp.di

import com.prmto.mova_movieapp.core.domain.use_case.GetMovieGenreListUseCase
import com.prmto.mova_movieapp.core.domain.use_case.GetTvGenreListUseCase
import com.prmto.mova_movieapp.feature_movie_tv_detail.data.link_extractor.BuyProviderLinkExtractor
import com.prmto.mova_movieapp.feature_movie_tv_detail.data.link_extractor.RentProviderLinkExtractor
import com.prmto.mova_movieapp.feature_movie_tv_detail.data.link_extractor.StreamProviderLinkExtractor
import com.prmto.mova_movieapp.feature_movie_tv_detail.data.remote.DetailApi
import com.prmto.mova_movieapp.feature_movie_tv_detail.data.repository.DetailRepositoryImpl
import com.prmto.mova_movieapp.feature_movie_tv_detail.domain.link_extractor.BaseLinkExtractor
import com.prmto.mova_movieapp.feature_movie_tv_detail.domain.repository.DetailRepository
import com.prmto.mova_movieapp.feature_movie_tv_detail.domain.use_cases.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DetailModule {

    @Provides
    @Singleton
    fun provideDetailApi(retrofit: Retrofit): DetailApi {
        return retrofit.create(DetailApi::class.java)
    }

    @Provides
    @Singleton
    fun provideDetailRepository(
        detailApi: DetailApi
    ): DetailRepository {
        return DetailRepositoryImpl(detailApi)
    }

    @Provides
    @Named("streamProvider")
    @Singleton
    fun provideStreamProviderLinkExtractor(): BaseLinkExtractor {
        return StreamProviderLinkExtractor()
    }

    @Provides
    @Named("buyProvider")
    @Singleton
    fun provideBuyProviderLinkExtractor(): BaseLinkExtractor {
        return BuyProviderLinkExtractor()
    }

    @Provides
    @Named("rentProvider")
    @Singleton
    fun provideRentProviderLinkExtractor(): BaseLinkExtractor {
        return RentProviderLinkExtractor()
    }

    @Provides
    @Singleton
    fun provideProviderLinkUseCase(
        @Named("streamProvider") streamProviderLinkExtractor: BaseLinkExtractor,
        @Named("buyProvider") buyProviderLinkExtractor: BaseLinkExtractor,
        @Named("rentProvider") rentProviderLinkExtractor: BaseLinkExtractor
    ): GetProviderLinksUseCase {
        return GetProviderLinksUseCase(
            streamProviderLinkExtractor = streamProviderLinkExtractor,
            buyProviderLinkExtractor = buyProviderLinkExtractor,
            rentProviderLinkExtractor = rentProviderLinkExtractor
        )
    }

    @Provides
    @Singleton
    fun provideDetailUseCases(
        detailRepository: DetailRepository,
        getMovieGenreListUseCase: GetMovieGenreListUseCase,
        getTvGenreListUseCase: GetTvGenreListUseCase,
        getProviderLinksUseCase: GetProviderLinksUseCase
    ): DetailUseCases {
        return DetailUseCases(
            movieDetailUseCase = GetMovieDetailUseCase(detailRepository, getProviderLinksUseCase),
            tvDetailUseCase = GetTvDetailUseCase(detailRepository, getProviderLinksUseCase),
            getMovieRecommendationUseCase = GetMovieRecommendationUseCase(
                detailRepository,
                getMovieGenreListUseCase
            ),
            getTvRecommendationUseCase = GetTvRecommendationUseCase(
                detailRepository,
                getTvGenreListUseCase
            ),
            getMovieVideosUseCase = GetMovieVideosUseCase(detailRepository),
            getTvVideosUseCase = GetTvVideosUseCase(detailRepository)
        )
    }


}