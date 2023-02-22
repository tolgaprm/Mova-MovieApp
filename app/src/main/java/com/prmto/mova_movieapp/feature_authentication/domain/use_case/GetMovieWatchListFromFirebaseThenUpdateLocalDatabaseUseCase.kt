package com.prmto.mova_movieapp.feature_authentication.domain.use_case

import com.prmto.mova_movieapp.R
import com.prmto.mova_movieapp.core.domain.repository.FirebaseCoreRepository
import com.prmto.mova_movieapp.core.domain.repository.LocalDatabaseRepository
import com.prmto.mova_movieapp.core.presentation.util.UiText
import com.prmto.mova_movieapp.feature_authentication.domain.repository.FirebaseMovieRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class GetMovieWatchListFromFirebaseThenUpdateLocalDatabaseUseCase @AssistedInject constructor(
    private val firebaseCoreRepository: FirebaseCoreRepository,
    private val firebaseMovieRepository: FirebaseMovieRepository,
    private val localDatabaseRepository: LocalDatabaseRepository,
    @Assisted private val coroutineScope: CoroutineScope = CoroutineScope(Dispatchers.IO)
) {

    operator fun invoke(
        onFailure: (uiText: UiText) -> Unit
    ) {
        val currentUser = firebaseCoreRepository.getCurrentUser()
        val userUid = currentUser?.uid
            ?: return onFailure(UiText.StringResource(R.string.error_user))

        firebaseMovieRepository.getMovieInWatchList(
            userUid = userUid,
            onSuccess = { moviesInWatchList ->
                moviesInWatchList.forEach { movieWatchListItem ->
                    coroutineScope.launch {
                        localDatabaseRepository.insertMovieToWatchList(movieWatchListItem)
                    }
                }
            },
            onFailure = onFailure
        )
    }
}