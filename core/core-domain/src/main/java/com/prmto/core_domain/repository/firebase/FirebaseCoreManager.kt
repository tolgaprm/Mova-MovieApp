package com.prmto.core_domain.repository.firebase

data class FirebaseCoreManager(
    val firebaseCoreRepository: FirebaseCoreRepository,
    val firebaseCoreMovieRepository: FirebaseCoreMovieRepository,
    val firebaseCoreTvSeriesRepository: FirebaseCoreTvSeriesRepository
)