package com.prmto.authentication_domain.use_case

import com.prmto.authentication_domain.repository.FirebaseMovieRepository
import com.prmto.core_domain.repository.firebase.FirebaseCoreRepository
import com.prmto.core_domain.repository.local.LocalDatabaseRepository
import com.prmto.core_domain.util.Resource
import com.prmto.core_domain.util.SimpleResource
import com.prmto.core_domain.util.UiText
import timber.log.Timber
import javax.inject.Inject
import com.prmto.core_domain.R as CoreDomainR

class GetFavoriteMovieFromFirebaseThenUpdateLocalDatabaseUseCase @Inject constructor(
    private val firebaseCoreRepository: FirebaseCoreRepository,
    private val firebaseMovieRepository: FirebaseMovieRepository,
    private val localDatabaseRepository: LocalDatabaseRepository,
) {

    suspend operator fun invoke(
    ): SimpleResource {
        val currentUser = firebaseCoreRepository.getCurrentUser()
        val userUid = currentUser?.uid
            ?: return Resource.Error(UiText.StringResource(CoreDomainR.string.error_user))

        val result = firebaseMovieRepository.getFavoriteMovie(
            userUid = userUid
        )

        return when (result) {
            is Resource.Error -> {
                Resource.Error(result.uiText ?: UiText.unknownError())
            }

            is Resource.Success -> {
                Timber.d(result.data.toString())
                result.data?.let {
                    it.forEach { movie ->
                        localDatabaseRepository.movieLocalRepository.insertMovieToFavoriteList(
                            movie = movie
                        )
                    }
                    Resource.Success(Unit)
                } ?: Resource.Error(UiText.unknownError())
            }
        }
    }
}