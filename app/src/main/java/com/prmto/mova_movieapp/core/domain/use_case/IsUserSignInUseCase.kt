package com.prmto.mova_movieapp.core.domain.use_case

import com.prmto.mova_movieapp.core.domain.repository.firebase.FirebaseCoreRepository
import javax.inject.Inject

class IsUserSignInUseCase @Inject constructor(
    private val repository: FirebaseCoreRepository
) {
    operator fun invoke(): Boolean {
        return repository.getCurrentUser() != null
    }
}