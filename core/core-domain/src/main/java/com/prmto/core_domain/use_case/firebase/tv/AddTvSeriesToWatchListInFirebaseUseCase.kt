package com.prmto.core_domain.use_case.firebase.tv

import com.prmto.core_domain.R
import com.prmto.core_domain.models.tv.TvSeries
import com.prmto.core_domain.repository.firebase.FirebaseCoreRepository
import com.prmto.core_domain.repository.firebase.FirebaseCoreTvSeriesRepository
import com.prmto.core_domain.util.Resource
import com.prmto.core_domain.util.SimpleResource
import com.prmto.core_domain.util.UiText
import javax.inject.Inject

class AddTvSeriesToWatchListInFirebaseUseCase @Inject constructor(
    private val firebaseCoreRepository: FirebaseCoreRepository,
    private val firebaseCoreTvSeriesRepository: FirebaseCoreTvSeriesRepository
) {

    suspend operator fun invoke(
        tvSeriesInWatchList: List<TvSeries>
    ): SimpleResource {
        val currentUser = firebaseCoreRepository.getCurrentUser()
        val userUid = currentUser?.uid
            ?: return Resource.Error(UiText.StringResource(R.string.must_login_able_to_add_in_list))

        return firebaseCoreTvSeriesRepository.addTvSeriesToWatchList(
            userUid = userUid,
            tvSeriesInWatchList = tvSeriesInWatchList
        )
    }
}