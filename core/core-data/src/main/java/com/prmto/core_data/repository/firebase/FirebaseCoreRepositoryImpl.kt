package com.prmto.core_data.repository.firebase

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.prmto.core_domain.repository.firebase.FirebaseCoreRepository
import javax.inject.Inject

class FirebaseCoreRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth
) : FirebaseCoreRepository {

    override fun getCurrentUser(): FirebaseUser? {
        return auth.currentUser
    }

    override fun signOut() {
        auth.signOut()
    }
}