package com.prmto.mova_movieapp.di

import com.prmto.mova_movieapp.core.domain.repository.DataStoreOperations
import com.prmto.mova_movieapp.core.domain.use_case.GetLanguageIsoCodeUseCase
import com.prmto.mova_movieapp.feature_person_detail.data.remote.PersonApi
import com.prmto.mova_movieapp.feature_person_detail.data.repository.PersonRepositoryImpl
import com.prmto.mova_movieapp.feature_person_detail.domain.repository.PersonRepository
import com.prmto.mova_movieapp.feature_person_detail.domain.use_case.GetPersonDetailUseCase
import com.prmto.mova_movieapp.feature_person_detail.domain.use_case.PersonDetailUseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PersonModule {

    @Provides
    @Singleton
    fun providePersonApi(retrofit: Retrofit): PersonApi {
        return retrofit.create(PersonApi::class.java)
    }

    @Provides
    @Singleton
    fun providesPersonRepository(personApi: PersonApi): PersonRepository {
        return PersonRepositoryImpl(personApi)
    }

    @Provides
    @Singleton
    fun providesPersonDetailUseCase(
        repository: PersonRepository,
        dataStoreOperations: DataStoreOperations
    ): PersonDetailUseCases {
        return PersonDetailUseCases(
            getPersonDetailUseCase = GetPersonDetailUseCase(repository),
            getLanguageIsoCodeUseCase = GetLanguageIsoCodeUseCase(dataStoreOperations)
        )
    }

}