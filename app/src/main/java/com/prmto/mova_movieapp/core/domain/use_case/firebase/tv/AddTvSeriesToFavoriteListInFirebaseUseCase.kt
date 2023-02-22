package com.prmto.mova_movieapp.core.domain.use_case.firebase.tv

import com.prmto.mova_movieapp.R
import com.prmto.mova_movieapp.core.domain.models.FavoriteTvSeries
import com.prmto.mova_movieapp.core.domain.repository.FirebaseCoreRepository
import com.prmto.mova_movieapp.core.domain.repository.FirebaseCoreTvSeriesRepository
import com.prmto.mova_movieapp.core.presentation.util.UiText
import com.prmto.mova_movieapp.core.util.Constants
import javax.inject.Inject

class AddTvSeriesToFavoriteListInFirebaseUseCase @Inject constructor(
    private val firebaseCoreRepository: FirebaseCoreRepository,
    private val firebaseCoreTvSeriesRepository: FirebaseCoreTvSeriesRepository
) {

    operator fun invoke(
        tvSeriesInFavoriteList: List<FavoriteTvSeries>,
        onSuccess: () -> Unit,
        onFailure: (uiText: UiText) -> Unit
    ) {
        val currentUser = firebaseCoreRepository.getCurrentUser()
        val userUid = currentUser?.uid
            ?: return onFailure(UiText.StringResource(R.string.must_login_able_to_add_in_list))

        val data = mapOf(
            Constants.FIREBASE_TV_SERIES_FIELD_NAME to tvSeriesInFavoriteList
        )

        firebaseCoreTvSeriesRepository.addTvSeriesToFavoriteList(
            userUid = userUid,
            data = data,
            onSuccess = onSuccess,
            onFailure = onFailure
        )
    }
}