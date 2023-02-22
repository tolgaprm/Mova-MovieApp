package com.prmto.mova_movieapp.core.domain.use_case.firebase.movie

import com.prmto.mova_movieapp.R
import com.prmto.mova_movieapp.core.domain.models.FavoriteMovie
import com.prmto.mova_movieapp.core.domain.repository.FirebaseCoreMovieRepository
import com.prmto.mova_movieapp.core.domain.repository.FirebaseCoreRepository
import com.prmto.mova_movieapp.core.presentation.util.UiText
import com.prmto.mova_movieapp.core.util.Constants.FIREBASE_MOVIES_FIELD_NAME
import javax.inject.Inject

class AddMovieToFavoriteListInFirebaseUseCase @Inject constructor(
    private val firebaseCoreRepository: FirebaseCoreRepository,
    private val firebaseCoreMovieRepository: FirebaseCoreMovieRepository
) {

    operator fun invoke(
        moviesInFavoriteList: List<FavoriteMovie>,
        onSuccess: () -> Unit,
        onFailure: (uiText: UiText) -> Unit
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