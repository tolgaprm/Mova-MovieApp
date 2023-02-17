package com.prmto.mova_movieapp.core.domain.use_case

import com.prmto.mova_movieapp.R
import com.prmto.mova_movieapp.core.domain.repository.FirebaseCoreRepository
import com.prmto.mova_movieapp.core.presentation.util.UiText
import com.prmto.mova_movieapp.core.util.Constants.FIREBASE_TV_SERIES_IDS_FIELD_NAME
import javax.inject.Inject

class AddTvSeriesToFavoriteListInFirebaseUseCase @Inject constructor(
    private val repository: FirebaseCoreRepository
) {

    operator fun invoke(
        tvSeriesIdsInFavoriteList: List<Int>,
        onSuccess: () -> Unit,
        onFailure: (uiText: UiText) -> Unit
    ) {
        val currentUser = repository.getCurrentUser()
        val userUid = currentUser?.uid
            ?: return onFailure(UiText.StringResource(R.string.must_login_able_to_add_in_list))

        val data = mapOf(
            FIREBASE_TV_SERIES_IDS_FIELD_NAME to tvSeriesIdsInFavoriteList
        )

        repository.addTvSeriesToFavoriteList(
            userUid = userUid,
            data = data,
            onSuccess = onSuccess,
            onFailure = onFailure
        )
    }
}