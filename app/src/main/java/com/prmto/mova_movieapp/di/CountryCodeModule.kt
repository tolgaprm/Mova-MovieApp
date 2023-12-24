package com.prmto.mova_movieapp.di

import android.content.Context
import com.prmto.mova_movieapp.core.util.countryCode.CountryCodeProvider
import com.prmto.mova_movieapp.core.util.countryCode.CountryCodeProviderImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CountryCodeModule {

    @Provides
    @Singleton
    fun bindCountryCodeProvider(
        @ApplicationContext context: Context
    ): CountryCodeProvider {
        return CountryCodeProviderImpl(context)
    }
}