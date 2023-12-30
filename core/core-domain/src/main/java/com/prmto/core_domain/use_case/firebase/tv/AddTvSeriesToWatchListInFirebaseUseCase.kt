package com.prmto.core_domain.use_case.firebase.tv

import com.prmto.core_domain.R
import com.prmto.core_domain.models.tv.TvSeries
import com.prmto.core_domain.repository.firebase.FirebaseCoreRepository
import com.prmto.core_domain.repository.firebase.FirebaseCoreTvSeriesRepository
import com.prmto.core_domain.util.Constants
import com.prmto.core_domain.util.UiText
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