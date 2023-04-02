package com.prmto.mova_movieapp.core.domain.use_case.firebase

import com.prmto.mova_movieapp.core.domain.repository.firebase.FirebaseCoreRepository
import javax.inject.Inject

class SignOutUseCase @Inject constructor(
    private val repository: FirebaseCoreRepository
) {
    operator fun invoke() {
        repository.signOut()
    }
}