package com.prmto.core_domain.use_case.firebase

import com.google.firebase.auth.FirebaseUser
import com.prmto.core_domain.repository.firebase.FirebaseCoreRepository
import javax.inject.Inject

class GetCurrentUserUseCase @Inject constructor(
    private val repository: FirebaseCoreRepository
) {
    operator fun invoke(): FirebaseUser? {
        return repository.getCurrentUser()
    }
}