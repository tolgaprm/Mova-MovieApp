package com.prmto.mova_movieapp.core.domain.use_case

data class FirebaseCoreUseCases(
    val getCurrentUserUseCase: GetCurrentUserUseCase,
    val signOutUseCase: SignOutUseCase,
    val isUserSignInUseCase: IsUserSignInUseCase
)
