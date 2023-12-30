package com.prmto.mova_movieapp.feature_settings.domain.di

import com.prmto.core_domain.repository.DataStoreOperations
import com.prmto.core_domain.use_case.languageIsoCode.GetLanguageIsoCodeUseCase
import com.prmto.core_domain.use_case.languageIsoCode.UpdateLanguageIsoCodeUseCase
import com.prmto.core_domain.use_case.uIMode.GetUIModeUseCase
import com.prmto.core_domain.use_case.uIMode.UpdateUIModeUseCase
import com.prmto.mova_movieapp.feature_settings.domain.use_case.SettingUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object SettingDomainModule {

    @Provides
    @ViewModelScoped
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