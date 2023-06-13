package com.prmto.mova_movieapp.feature_upcoming.di

import com.prmto.mova_movieapp.core.data.data_source.local.MovaDatabase
import com.prmto.mova_movieapp.core.domain.use_case.GetMovieGenreListUseCase
import com.prmto.mova_movieapp.feature_upcoming.data.remote.UpComingApi
import com.prmto.mova_movieapp.feature_upcoming.data.repository.UpComingLocalRepositoryImpl
import com.prmto.mova_movieapp.feature_upcoming.data.repository.UpComingRepositoryImpl
import com.prmto.mova_movieapp.feature_upcoming.domain.repository.UpComingLocalRepository
import com.prmto.mova_movieapp.feature_upcoming.domain.repository.UpComingRepository
import com.prmto.mova_movieapp.feature_upcoming.domain.use_case.DeleteUpcomingRemindUseCase
import com.prmto.mova_movieapp.feature_upcoming.domain.use_case.GetUpcomingMovieUseCase
import com.prmto.mova_movieapp.feature_upcoming.domain.use_case.GetUpcomingRemindMovieUseCase
import com.prmto.mova_movieapp.feature_upcoming.domain.use_case.InsertUpComingRemindUseCase
import com.prmto.mova_movieapp.feature_upcoming.domain.use_case.UpComingUseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import retrofit2.Retrofit

@Module
@InstallIn(ViewModelComponent::class)
object UpComingModule {

    @Provides
    @ViewModelScoped
    fun provideUpComingApi(retrofit: Retrofit): UpComingApi {
        return retrofit.create(UpComingApi::class.java)
    }

    @Provides
    @ViewModelScoped
    fun provideUpComingRepository(
        api: UpComingApi
    ): UpComingRepository {
        return UpComingRepositoryImpl(
            api = api
        )
    }

    @Provides
    @ViewModelScoped
    fun provideUpComingLocalRepository(
        movaDatabase: MovaDatabase
    ): UpComingLocalRepository {
        return UpComingLocalRepositoryImpl(
            movaDatabase = movaDatabase
        )
    }

    @Provides
    @ViewModelScoped
    fun provideUpComingUseCases(
        upComingRepository: UpComingRepository,
        upComingLocalRepository: UpComingLocalRepository,
        movieGenreListUseCase: GetMovieGenreListUseCase
    ): UpComingUseCases {
        return UpComingUseCases(
            deleteUpcomingRemindUseCase = DeleteUpcomingRemindUseCase(
                upComingLocalRepository = upComingLocalRepository
            ),
            insertUpComingRemindUseCase = InsertUpComingRemindUseCase(
                upComingLocalRepository = upComingLocalRepository

            ),
            getUpcomingMovieUseCase = GetUpcomingMovieUseCase(
                repository = upComingRepository,
                movieGenreListUseCase = movieGenreListUseCase,
                getUpcomingRemindMovieUseCase = GetUpcomingRemindMovieUseCase(
                    upComingLocalRepository = upComingLocalRepository
                )
            )
        )
    }
}