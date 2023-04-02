package com.prmto.mova_movieapp.core.data.repository.firebase

import com.prmto.mova_movieapp.core.domain.repository.firebase.FirebaseCoreMovieRepository
import com.prmto.mova_movieapp.core.domain.repository.firebase.FirebaseCoreRepository
import com.prmto.mova_movieapp.core.domain.repository.firebase.FirebaseCoreTvSeriesRepository

data class FirebaseCoreManager(
    val firebaseCoreRepository: FirebaseCoreRepository,
    val firebaseCoreMovieRepository: FirebaseCoreMovieRepository,
    val firebaseCoreTvSeriesRepository: FirebaseCoreTvSeriesRepository
)
