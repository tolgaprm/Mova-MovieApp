package com.prmto.mova_movieapp.core.data.repository

import com.prmto.mova_movieapp.core.domain.repository.FirebaseCoreMovieRepository
import com.prmto.mova_movieapp.core.domain.repository.FirebaseCoreRepository
import com.prmto.mova_movieapp.core.domain.repository.FirebaseCoreTvSeriesRepository

data class FirebaseCoreManager(
    val firebaseCoreRepository: FirebaseCoreRepository,
    val firebaseCoreMovieRepository: FirebaseCoreMovieRepository,
    val firebaseCoreTvSeriesRepository: FirebaseCoreTvSeriesRepository
)
