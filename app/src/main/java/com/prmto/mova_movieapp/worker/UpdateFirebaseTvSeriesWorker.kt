package com.prmto.mova_movieapp.worker

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.prmto.core_domain.use_case.firebase.tv.GetFavoriteTvSeriesFromLocalDatabaseThenUpdateToFirebase
import com.prmto.core_domain.use_case.firebase.tv.GetTvSeriesWatchFromLocalDatabaseThenUpdateToFirebase
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@HiltWorker
class UpdateFirebaseTvSeriesWorker @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted workerParams: WorkerParameters,
    private val getFavoriteTvSeriesFromLocalDatabaseThenUpdateToFirebase: GetFavoriteTvSeriesFromLocalDatabaseThenUpdateToFirebase,
    private val getTvSeriesWatchFromLocalDatabaseThenUpdateToFirebase: GetTvSeriesWatchFromLocalDatabaseThenUpdateToFirebase
) : CoroutineWorker(appContext, workerParams) {

    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    override suspend fun doWork(): Result {

        var error = false

        coroutineScope.launch {
            getFavoriteTvSeriesFromLocalDatabaseThenUpdateToFirebase(
                onSuccess = { error = false },
                onFailure = { error = true }
            )
        }

        coroutineScope.launch {
            getTvSeriesWatchFromLocalDatabaseThenUpdateToFirebase(
                onSuccess = { error = false },
                onFailure = { error = true }
            )
        }

        return if (error) Result.failure() else Result.success()
    }
}