package com.prmto.mova_movieapp.feature_authentication.domain.use_case

import com.prmto.mova_movieapp.R
import com.prmto.mova_movieapp.core.domain.repository.FirebaseCoreRepository
import com.prmto.mova_movieapp.core.domain.repository.LocalDatabaseRepository
import com.prmto.mova_movieapp.core.presentation.util.UiText
import com.prmto.mova_movieapp.feature_authentication.domain.repository.FirebaseMovieRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

class GetFavoriteMovieFromFirebaseThenUpdateLocalDatabaseUseCase @Inject constructor(
    private val firebaseCoreRepository: FirebaseCoreRepository,
    private val firebaseMovieRepository: FirebaseMovieRepository,
    private val localDatabaseRepository: LocalDatabaseRepository
) {

    operator fun invoke(
        onFailure: (uiText: UiText) -> Unit,
        coroutineScope: CoroutineScope
    ) {
        val currentUser = firebaseCoreRepository.getCurrentUser()
        val userUid = currentUser?.uid
            ?: return onFailure(UiText.StringResource(R.string.error_user))

        firebaseMovieRepository.getFavoriteMovie(
            userUid = userUid,
            onSuccess = { favoriteMovies ->
                favoriteMovies.forEach { favoriteMovie ->
                    coroutineScope.launch {
                        localDatabaseRepository.insertMovieToFavoriteList(favoriteMovie)
                    }
                }
            },
            onFailure = onFailure
        )
    }
}