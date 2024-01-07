package com.prmto.work_manager

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.prmto.core_domain.use_case.firebase.movie.GetFavoriteMoviesFromLocalDatabaseThenUpdateToFirebaseUseCase
import com.prmto.core_domain.use_case.firebase.movie.GetMovieWatchListFromLocalDatabaseThenUpdateToFirebase
import com.prmto.core_domain.util.Resource
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@HiltWorker
class UpdateFirebaseMovieWorker @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted workerParams: WorkerParameters,
    private val getFavoriteMoviesFromLocalDatabaseThenUpdateToFirebaseUseCase: GetFavoriteMoviesFromLocalDatabaseThenUpdateToFirebaseUseCase,
    private val getMovieWatchListFromLocalDatabaseThenUpdateToFirebase: GetMovieWatchListFromLocalDatabaseThenUpdateToFirebase
) : CoroutineWorker(appContext, workerParams) {

    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    override suspend fun doWork(): Result {

        var error = false

        coroutineScope.launch {
            error = when (getFavoriteMoviesFromLocalDatabaseThenUpdateToFirebaseUseCase()) {
                is Resource.Error -> true

                is Resource.Success -> false
            }
        }

        coroutineScope.launch {
            error = when (getMovieWatchListFromLocalDatabaseThenUpdateToFirebase()) {
                is Resource.Error -> true

                is Resource.Success -> false
            }
        }

        return if (error) Result.failure() else Result.success()
    }
}