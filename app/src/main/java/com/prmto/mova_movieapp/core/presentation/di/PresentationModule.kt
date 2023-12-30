package com.prmto.mova_movieapp.core.presentation.di

import android.content.Context
import coil.ImageLoader
import com.prmto.mova_movieapp.R
import com.prmto.mova_movieapp.core.presentation.countryCode.CountryCodeProvider
import com.prmto.mova_movieapp.core.presentation.countryCode.CountryCodeProviderImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PresentationModule {

    @Provides
    @Singleton
    fun provideImageLoader(@ApplicationContext context: Context): ImageLoader {
        return ImageLoader.Builder(context).crossfade(500).placeholder(R.drawable.loading_animate)
            .build()
    }

    @Provides
    @Singleton
    fun bindCountryCodeProvider(
        @ApplicationContext context: Context
    ): CountryCodeProvider {
        return CountryCodeProviderImpl(context)
    }
}