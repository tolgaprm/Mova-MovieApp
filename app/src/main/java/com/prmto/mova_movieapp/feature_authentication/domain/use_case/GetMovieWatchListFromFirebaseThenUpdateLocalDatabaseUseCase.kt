package com.prmto.mova_movieapp.feature_authentication.domain.use_case

import com.prmto.core_domain.repository.firebase.FirebaseCoreRepository
import com.prmto.core_domain.repository.local.LocalDatabaseRepository
import com.prmto.core_domain.util.UiText
import com.prmto.mova_movieapp.R
import com.prmto.mova_movieapp.feature_authentication.domain.repository.FirebaseMovieRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

class GetMovieWatchListFromFirebaseThenUpdateLocalDatabaseUseCase @Inject constructor(
    private val firebaseCoreRepository: FirebaseCoreRepository,
    private val firebaseMovieRepository: FirebaseMovieRepository,
    private val localDatabaseRepository: LocalDatabaseRepository,
) {

    operator fun invoke(
        onFailure: (uiText: UiText) -> Unit,
        coroutineScope: CoroutineScope,
    ) {
        val currentUser = firebaseCoreRepository.getCurrentUser()
        val userUid = currentUser?.uid
            ?: return onFailure(UiText.StringResource(R.string.error_user))

        firebaseMovieRepository.getMovieInWatchList(
            userUid = userUid,
            onSuccess = { movies ->
                movies.forEach { movie ->
                    coroutineScope.launch {
                        localDatabaseRepository.movieLocalRepository.insertMovieToWatchList(
                            movie = movie
                        )
                    }
                }
            },
            onFailure = onFailure
        )
    }
}