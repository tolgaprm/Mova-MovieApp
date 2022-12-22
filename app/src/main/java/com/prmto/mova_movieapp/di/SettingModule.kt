package com.prmto.mova_movieapp.di

import com.prmto.mova_movieapp.core.domain.repository.DataStoreOperations
import com.prmto.mova_movieapp.core.domain.use_case.GetLanguageIsoCodeUseCase
import com.prmto.mova_movieapp.core.domain.use_case.GetUIModeUseCase
import com.prmto.mova_movieapp.core.domain.use_case.UpdateLanguageIsoCodeUseCase
import com.prmto.mova_movieapp.core.domain.use_case.UpdateUIModeUseCase
import com.prmto.mova_movieapp.feature_settings.domain.use_case.SettingUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SettingModule {

    @Provides
    @Singleton
    fun provideSettingsUseCases(
        dataStoreOperations: DataStoreOperations
    ): SettingUseCase {
        return SettingUseCase(
            getUIModeUseCase = GetUIModeUseCase(dataStoreOperations),
            updateUIModeUseCase = UpdateUIModeUseCase(dataStoreOperations),
            updateLanguageIsoCodeUseCase = UpdateLanguageIsoCodeUseCase(dataStoreOperations),
            getLanguageIsoCodeUseCase = GetLanguageIsoCodeUseCase(dataStoreOperations)
        )
    }
}