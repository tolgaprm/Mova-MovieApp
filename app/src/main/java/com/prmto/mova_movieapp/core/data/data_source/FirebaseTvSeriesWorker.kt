package com.prmto.mova_movieapp.core.data.data_source

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.prmto.mova_movieapp.core.domain.use_case.FirebaseCoreUseCases
import com.prmto.mova_movieapp.core.domain.use_case.LocalDatabaseUseCases
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@HiltWorker
class FirebaseTvSeriesWorker @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted workerParams: WorkerParameters,
    private val firebaseCoreUseCases: FirebaseCoreUseCases,
    private val localDatabaseUseCases: LocalDatabaseUseCases
) : CoroutineWorker(appContext, workerParams) {

    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    override suspend fun doWork(): Result {

        var error: Boolean = false

        coroutineScope.launch {
            localDatabaseUseCases.getFavoriteTvSeriesIdsUseCase()
                .collectLatest { favoriteTvSeriesIds ->
                    firebaseCoreUseCases.addTvSeriesToFavoriteListInFirebaseUseCase(
                        tvSeriesIdsInFavoriteList = favoriteTvSeriesIds,
                        onSuccess = { error = false },
                        onFailure = { error = true }
                    )
                }
        }

        coroutineScope.launch {
            localDatabaseUseCases.getTvSeriesWatchListItemIdsUseCase()
                .collectLatest { tvSeriesIdsInWatchList ->
                    firebaseCoreUseCases.addTvSeriesToWatchListInFirebaseUseCase(
                        tvSeriesIdsInWatchList = tvSeriesIdsInWatchList,
                        onSuccess = { error = false },
                        onFailure = { error = true }

                    )
                }
        }

        return if (error) Result.failure() else Result.success()
    }
}