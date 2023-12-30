package com.prmto.core_domain.use_case.firebase

import com.prmto.core_domain.use_case.firebase.movie.AddMovieToFavoriteListInFirebaseUseCase
import com.prmto.core_domain.use_case.firebase.movie.AddMovieToWatchListInFirebaseUseCase
import com.prmto.core_domain.use_case.firebase.tv.AddTvSeriesToFavoriteListInFirebaseUseCase
import com.prmto.core_domain.use_case.firebase.tv.AddTvSeriesToWatchListInFirebaseUseCase

data class FirebaseCoreUseCases(
    val getCurrentUserUseCase: GetCurrentUserUseCase,
    val signOutUseCase: SignOutUseCase,
    val isUserSignInUseCase: IsUserSignInUseCase,
    val addMovieToFavoriteListInFirebaseUseCase: AddMovieToFavoriteListInFirebaseUseCase,
    val addMovieToWatchListInFirebaseUseCase: AddMovieToWatchListInFirebaseUseCase,
    val addTvSeriesToFavoriteListInFirebaseUseCase: AddTvSeriesToFavoriteListInFirebaseUseCase,
    val addTvSeriesToWatchListInFirebaseUseCase: AddTvSeriesToWatchListInFirebaseUseCase
)