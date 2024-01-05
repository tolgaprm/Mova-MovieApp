package com.prmto.person_detail_domain.di

import com.prmto.core_domain.repository.DataStoreOperations
import com.prmto.core_domain.use_case.languageIsoCode.GetLanguageIsoCodeUseCase
import com.prmto.person_detail_domain.repository.PersonRepository
import com.prmto.person_detail_domain.use_case.GetPersonDetailUseCase
import com.prmto.person_detail_domain.use_case.PersonDetailUseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object PersonDetailDomainModule {

    @Provides
    @ViewModelScoped
    fun providesPersonDetailUseCase(
        repository: PersonRepository,
        dataStoreOperations: DataStoreOperations
    ): PersonDetailUseCases {
        return PersonDetailUseCases(
            getPersonDetailUseCase = GetPersonDetailUseCase(
                repository
            ),
            getLanguageIsoCodeUseCase = GetLanguageIsoCodeUseCase(dataStoreOperations)
        )
    }
}