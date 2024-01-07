package com.prmto.authentication_domain.use_case

import com.prmto.authentication_domain.repository.FirebaseMovieRepository
import com.prmto.core_domain.repository.firebase.FirebaseCoreRepository
import com.prmto.core_domain.repository.local.LocalDatabaseRepository
import com.prmto.core_domain.util.Resource
import com.prmto.core_domain.util.SimpleResource
import com.prmto.core_domain.util.UiText
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import javax.inject.Inject
import com.prmto.core_domain.R as CoreDomainR

class GetMovieWatchListFromFirebaseThenUpdateLocalDatabaseUseCase @Inject constructor(
    private val firebaseCoreRepository: FirebaseCoreRepository,
    private val firebaseMovieRepository: FirebaseMovieRepository,
    private val localDatabaseRepository: LocalDatabaseRepository,
) {

    suspend operator fun invoke(): SimpleResource {
        val currentUser = firebaseCoreRepository.getCurrentUser()
        val userUid = currentUser?.uid
            ?: return Resource.Error(UiText.StringResource(CoreDomainR.string.error_user))

        val result = firebaseMovieRepository.getMovieInWatchList(
            userUid = userUid
        )

        return when (result) {
            is Resource.Error -> {
                Resource.Error(result.uiText ?: UiText.unknownError())
            }

            is Resource.Success -> {
                result.data?.let {
                    it.forEach { movie ->
                        coroutineScope {
                            async {
                                localDatabaseRepository.movieLocalRepository.insertMovieToWatchList(
                                    movie = movie
                                )
                            }.await()
                        }
                    }
                    Resource.Success(Unit)
                } ?: Resource.Error(UiText.unknownError())
            }
        }
    }
}