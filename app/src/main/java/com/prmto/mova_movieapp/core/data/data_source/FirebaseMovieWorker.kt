package com.prmto.mova_movieapp.core.data.data_source

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.prmto.mova_movieapp.core.domain.use_case.FirebaseCoreUseCases
import com.prmto.mova_movieapp.core.domain.use_case.LocalDatabaseUseCases
import com.prmto.mova_movieapp.core.presentation.util.asString
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber

@HiltWorker
class FirebaseMovieWorker @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted workerParams: WorkerParameters,
    private val firebaseCoreUseCases: FirebaseCoreUseCases,
    private val localDatabaseUseCases: LocalDatabaseUseCases
) : CoroutineWorker(appContext, workerParams) {

    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    override suspend fun doWork(): Result {

        var error: Boolean = false

        coroutineScope.launch {
            localDatabaseUseCases.getFavoriteMovieIdsUseCase().collectLatest { favoriteMovieIds ->
                firebaseCoreUseCases.addMovieToFavoriteListInFirebaseUseCase(
                    movieIdsInFavoriteList = favoriteMovieIds,
                    onSuccess = { error = false },
                    onFailure = { error = true;Timber.d(it.asString(applicationContext)) }
                )
            }
        }

        coroutineScope.launch {
            localDatabaseUseCases.getMovieWatchListItemIdsUseCase()
                .collectLatest { movieIdsInWatchList ->
                    firebaseCoreUseCases.addMovieToWatchListInFirebaseUseCase(
                        movieIdsInWatchList = movieIdsInWatchList,
                        onSuccess = { error = false },
                        onFailure = { error = true }
                    )
                }
        }

        return if (error) Result.failure() else Result.success()
    }
}