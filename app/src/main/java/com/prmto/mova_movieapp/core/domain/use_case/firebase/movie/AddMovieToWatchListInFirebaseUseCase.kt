package com.prmto.mova_movieapp.core.domain.use_case.firebase.movie

import com.prmto.mova_movieapp.R
import com.prmto.mova_movieapp.core.domain.models.Movie
import com.prmto.mova_movieapp.core.domain.repository.firebase.FirebaseCoreMovieRepository
import com.prmto.mova_movieapp.core.domain.repository.firebase.FirebaseCoreRepository
import com.prmto.mova_movieapp.core.presentation.util.UiText
import com.prmto.mova_movieapp.core.util.Constants
import javax.inject.Inject

class AddMovieToWatchListInFirebaseUseCase @Inject constructor(
    private val firebaseCoreRepository: FirebaseCoreRepository,
    private val firebaseCoreMovieRepository: FirebaseCoreMovieRepository
) {

    operator fun invoke(
        moviesInWatchList: List<Movie>,
        onSuccess: () -> Unit,
        onFailure: (uiText: UiText) -> Unit,
    ) {
        val currentUser = firebaseCoreRepository.getCurrentUser()
        val userUid = currentUser?.uid
            ?: return onFailure(UiText.StringResource(R.string.must_login_able_to_add_in_list))

        val data = mapOf(
            Constants.FIREBASE_MOVIES_FIELD_NAME to moviesInWatchList
        )

        firebaseCoreMovieRepository.addMovieToWatchList(
            userUid = userUid,
            data = data,
            onSuccess = onSuccess,
            onFailure = onFailure
        )
    }
}