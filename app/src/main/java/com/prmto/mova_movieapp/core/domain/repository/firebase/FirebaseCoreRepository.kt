package com.prmto.mova_movieapp.core.domain.repository.firebase

import com.google.firebase.auth.FirebaseUser

interface FirebaseCoreRepository {
    fun getCurrentUser(): FirebaseUser?
    fun signOut()
}