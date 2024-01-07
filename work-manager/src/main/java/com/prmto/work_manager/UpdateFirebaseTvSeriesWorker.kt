package com.prmto.work_manager

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.prmto.core_domain.use_case.firebase.tv.GetFavoriteTvSeriesFromLocalDatabaseThenUpdateToFirebase
import com.prmto.core_domain.use_case.firebase.tv.GetTvSeriesWatchFromLocalDatabaseThenUpdateToFirebase
import com.prmto.core_domain.util.Resource
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
            error = when (getFavoriteTvSeriesFromLocalDatabaseThenUpdateToFirebase()) {
                is Resource.Error -> true

                is Resource.Success -> false
            }
        }

        coroutineScope.launch {
            error = when (getTvSeriesWatchFromLocalDatabaseThenUpdateToFirebase()) {
                is Resource.Error -> true

                is Resource.Success -> false
            }
        }

        return if (error) Result.failure() else Result.success()
    }
}