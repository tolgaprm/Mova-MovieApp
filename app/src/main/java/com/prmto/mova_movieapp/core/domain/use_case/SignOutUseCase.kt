package com.prmto.mova_movieapp.core.domain.use_case

import com.prmto.mova_movieapp.core.domain.repository.FirebaseCoreRepository
import javax.inject.Inject

class SignOutUseCase @Inject constructor(
    private val repository: FirebaseCoreRepository
) {
    operator fun invoke() {
        repository.signOut()
    }
}