package com.prmto.mova_movieapp.di

import coil.ImageLoader
import com.prmto.mova_movieapp.feature_explore.presentation.adapter.FilterMoviesAdapter
import com.prmto.mova_movieapp.feature_explore.presentation.adapter.FilterTvSeriesAdapter
import com.prmto.mova_movieapp.feature_explore.presentation.adapter.SearchRecyclerAdapter
import com.prmto.mova_movieapp.feature_home.presentation.home.adapter.*
import com.prmto.mova_movieapp.feature_movie_tv_detail.presentation.detail.adapter.DetailActorAdapter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import dagger.hilt.android.scopes.FragmentScoped

@Module
@InstallIn(FragmentComponent::class)
object AdapterModule {
    @Provides
    @FragmentScoped
    fun provideNowPlayingAdapter(
        imageLoader: ImageLoader
    ): NowPlayingRecyclerAdapter {
        return NowPlayingRecyclerAdapter(imageLoader)
    }

    @Provides
    @FragmentScoped
    fun providePopularMoviesAdapter(
        imageLoader: ImageLoader
    ): PopularMoviesAdapter {
        return PopularMoviesAdapter(imageLoader)
    }

    @Provides
    @FragmentScoped
    fun providePopularTvSeriesAdapter(
        imageLoader: ImageLoader
    ): PopularTvSeriesAdapter {
        return PopularTvSeriesAdapter(imageLoader)
    }

    @Provides
    @FragmentScoped
    fun provideTopRatedMoviesAdapter(
        imageLoader: ImageLoader
    ): TopRatedMoviesAdapter {
        return TopRatedMoviesAdapter(imageLoader)
    }

    @Provides
    @FragmentScoped
    fun provideTopRatedTvAdapter(
        imageLoader: ImageLoader
    ): TopRatedTvSeriesAdapter {
        return TopRatedTvSeriesAdapter(imageLoader)
    }

    @Provides
    @FragmentScoped
    fun provideDetailActorAdapter(
        imageLoader: ImageLoader
    ): DetailActorAdapter {
        return DetailActorAdapter(imageLoader)
    }

    @Provides
    @FragmentScoped
    fun provideSearchRecyclerAdapter(
        imageLoader: ImageLoader
    ): SearchRecyclerAdapter {
        return SearchRecyclerAdapter(imageLoader)
    }

    @Provides
    @FragmentScoped
    fun provideSearchMoviesAdapter(
        imageLoader: ImageLoader
    ): FilterMoviesAdapter {
        return FilterMoviesAdapter(imageLoader)
    }

    @Provides
    @FragmentScoped
    fun provideSearchTvSeriesAdapter(
        imageLoader: ImageLoader
    ): FilterTvSeriesAdapter {
        return FilterTvSeriesAdapter(imageLoader)
    }
}