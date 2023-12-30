package com.prmto.core_domain.use_case.firebase

import com.prmto.core_domain.repository.firebase.FirebaseCoreRepository
import javax.inject.Inject

class SignOutUseCase @Inject constructor(
    private val repository: FirebaseCoreRepository
) {
    operator fun invoke() {
        repository.signOut()
    }
}