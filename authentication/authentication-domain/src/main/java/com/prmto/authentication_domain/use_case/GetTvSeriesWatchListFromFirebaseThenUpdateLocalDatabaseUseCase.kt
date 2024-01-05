package com.prmto.authentication_domain.use_case

import com.prmto.authentication_domain.repository.FirebaseTvSeriesRepository
import com.prmto.core_domain.repository.firebase.FirebaseCoreRepository
import com.prmto.core_domain.repository.local.LocalDatabaseRepository
import com.prmto.core_domain.util.UiText
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.prmto.core_domain.R as CoreDomainR

class GetTvSeriesWatchListFromFirebaseThenUpdateLocalDatabaseUseCase @Inject constructor(
    private val firebaseCoreRepository: FirebaseCoreRepository,
    private val firebaseTvSeriesRepository: FirebaseTvSeriesRepository,
    private val localDatabaseRepository: LocalDatabaseRepository,
) {

    operator fun invoke(
        onFailure: (uiText: UiText) -> Unit,
        coroutineScope: CoroutineScope,
    ) {
        val currentUser = firebaseCoreRepository.getCurrentUser()
        val userUid = currentUser?.uid
            ?: return onFailure(UiText.StringResource(CoreDomainR.string.error_user))

        firebaseTvSeriesRepository.getTvSeriesInWatchList(
            userUid = userUid,
            onSuccess = { tvSeries ->
                tvSeries.forEach { tvSeriesItem ->
                    coroutineScope.launch {
                        localDatabaseRepository.tvSeriesLocalRepository.insertTvSeriesToWatchListItem(
                            tvSeries = tvSeriesItem
                        )
                    }
                }
            },
            onFailure = onFailure
        )
    }
}