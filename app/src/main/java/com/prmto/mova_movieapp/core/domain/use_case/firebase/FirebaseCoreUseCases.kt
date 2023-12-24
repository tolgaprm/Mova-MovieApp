package com.prmto.mova_movieapp.core.domain.use_case.firebase

import com.prmto.mova_movieapp.core.domain.use_case.firebase.movie.AddMovieToFavoriteListInFirebaseUseCase
import com.prmto.mova_movieapp.core.domain.use_case.firebase.movie.AddMovieToWatchListInFirebaseUseCase
import com.prmto.mova_movieapp.core.domain.use_case.firebase.tv.AddTvSeriesToFavoriteListInFirebaseUseCase
import com.prmto.mova_movieapp.core.domain.use_case.firebase.tv.AddTvSeriesToWatchListInFirebaseUseCase

data class FirebaseCoreUseCases(
    val getCurrentUserUseCase: GetCurrentUserUseCase,
    val signOutUseCase: SignOutUseCase,
    val isUserSignInUseCase: IsUserSignInUseCase,
    val addMovieToFavoriteListInFirebaseUseCase: AddMovieToFavoriteListInFirebaseUseCase,
    val addMovieToWatchListInFirebaseUseCase: AddMovieToWatchListInFirebaseUseCase,
    val addTvSeriesToFavoriteListInFirebaseUseCase: AddTvSeriesToFavoriteListInFirebaseUseCase,
    val addTvSeriesToWatchListInFirebaseUseCase: AddTvSeriesToWatchListInFirebaseUseCase
)
