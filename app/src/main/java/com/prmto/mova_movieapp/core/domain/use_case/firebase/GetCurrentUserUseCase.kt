package com.prmto.mova_movieapp.core.domain.use_case.firebase

import com.google.firebase.auth.FirebaseUser
import com.prmto.mova_movieapp.core.domain.repository.firebase.FirebaseCoreRepository
import javax.inject.Inject

class GetCurrentUserUseCase @Inject constructor(
    private val repository: FirebaseCoreRepository
) {
    operator fun invoke(): FirebaseUser? {
        return repository.getCurrentUser()
    }
}