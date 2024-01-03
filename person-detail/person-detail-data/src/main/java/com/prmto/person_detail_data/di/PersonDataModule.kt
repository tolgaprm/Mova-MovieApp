package com.prmto.person_detail_data.di

import com.prmto.person_detail_data.remote.datasource.PersonRemoteDataSource
import com.prmto.person_detail_data.remote.datasource.PersonRemoteDataSourceImpl
import com.prmto.person_detail_data.repository.PersonRepositoryImpl
import com.prmto.person_detail_domain.repository.PersonRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
abstract class PersonDataModule {
    @Binds
    @ViewModelScoped
    abstract fun bindPersonRemoteDataSource(
        personRemoteDataSourceImpl: PersonRemoteDataSourceImpl
    ): PersonRemoteDataSource

    @Binds
    @ViewModelScoped
    abstract fun bindPersonRepository(
        personRepositoryImpl: PersonRepositoryImpl
    ): PersonRepository
}