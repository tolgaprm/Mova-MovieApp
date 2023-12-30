package com.prmto.core_domain.use_case.firebase.movie

import com.prmto.core_domain.R
import com.prmto.core_domain.models.movie.Movie
import com.prmto.core_domain.repository.firebase.FirebaseCoreMovieRepository
import com.prmto.core_domain.repository.firebase.FirebaseCoreRepository
import com.prmto.core_domain.util.Constants.FIREBASE_MOVIES_FIELD_NAME
import com.prmto.core_domain.util.UiText
import javax.inject.Inject

class AddMovieToFavoriteListInFirebaseUseCase @Inject constructor(
    private val firebaseCoreRepository: FirebaseCoreRepository,
    private val firebaseCoreMovieRepository: FirebaseCoreMovieRepository
) {

    operator fun invoke(
        moviesInFavoriteList: List<Movie>,
        onSuccess: () -> Unit,
        onFailure: (uiText: UiText) -> Unit,
    ) {
        val currentUser = firebaseCoreRepository.getCurrentUser()
        val userUid = currentUser?.uid
            ?: return onFailure(UiText.StringResource(R.string.must_login_able_to_add_in_list))


        val data = mapOf(
            FIREBASE_MOVIES_FIELD_NAME to moviesInFavoriteList
        )

        firebaseCoreMovieRepository.addMovieToFavoriteList(
            userUid = userUid,
            data = data,
            onSuccess = onSuccess,
            onFailure = onFailure
        )
    }
}