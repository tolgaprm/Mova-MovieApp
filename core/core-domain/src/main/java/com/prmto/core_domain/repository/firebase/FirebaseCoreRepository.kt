package com.prmto.core_domain.repository.firebase

import com.google.firebase.auth.FirebaseUser

interface FirebaseCoreRepository {
    fun getCurrentUser(): FirebaseUser?
    fun signOut()
}