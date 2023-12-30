package com.prmto.mova_movieapp.core.domain.use_case.firebase.tv

import com.prmto.mova_movieapp.R
import com.prmto.mova_movieapp.core.domain.models.tv.TvSeries
import com.prmto.mova_movieapp.core.domain.repository.firebase.FirebaseCoreRepository
import com.prmto.mova_movieapp.core.domain.repository.firebase.FirebaseCoreTvSeriesRepository
import com.prmto.mova_movieapp.core.domain.util.Constants
import com.prmto.mova_movieapp.core.domain.util.UiText
import javax.inject.Inject

class AddTvSeriesToWatchListInFirebaseUseCase @Inject constructor(
    private val firebaseCoreRepository: FirebaseCoreRepository,
    private val firebaseCoreTvSeriesRepository: FirebaseCoreTvSeriesRepository
) {

    operator fun invoke(
        tvSeriesInWatchList: List<TvSeries>,
        onSuccess: () -> Unit,
        onFailure: (uiText: UiText) -> Unit,
    ) {
        val currentUser = firebaseCoreRepository.getCurrentUser()
        val userUid = currentUser?.uid
            ?: return onFailure(UiText.StringResource(R.string.must_login_able_to_add_in_list))

        val data = mapOf(
            Constants.FIREBASE_TV_SERIES_FIELD_NAME to tvSeriesInWatchList
        )

        firebaseCoreTvSeriesRepository.addTvSeriesToWatchList(
            userUid = userUid,
            data = data,
            onSuccess = onSuccess,
            onFailure = onFailure
        )
    }
}