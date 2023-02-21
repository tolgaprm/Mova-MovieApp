package com.prmto.mova_movieapp.core.data.data_source

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.prmto.mova_movieapp.core.domain.use_case.firebase.movie.GetFavoriteMovieIdsFromLocalDatabaseThenUpdateToFirebaseUseCase
import com.prmto.mova_movieapp.core.domain.use_case.firebase.movie.GetMovieWatchListFromLocalDatabaseThenUpdateToFirebase
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@HiltWorker
class FirebaseMovieWorker @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted workerParams: WorkerParameters,
    private val getFavoriteMovieIdsFromLocalDatabaseThenUpdateToFirebaseUseCase: GetFavoriteMovieIdsFromLocalDatabaseThenUpdateToFirebaseUseCase,
    private val getMovieWatchListFromLocalDatabaseThenUpdateToFirebase: GetMovieWatchListFromLocalDatabaseThenUpdateToFirebase
) : CoroutineWorker(appContext, workerParams) {

    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    override suspend fun doWork(): Result {

        var error: Boolean = false

        coroutineScope.launch {
            getFavoriteMovieIdsFromLocalDatabaseThenUpdateToFirebaseUseCase(
                onSuccess = { error = false },
                onFailure = { error = true }
            )

        }

        coroutineScope.launch {
            getMovieWatchListFromLocalDatabaseThenUpdateToFirebase(
                onSuccess = { error = false },
                onFailure = { error = true }
            )
        }

        return if (error) Result.failure() else Result.success()
    }
}