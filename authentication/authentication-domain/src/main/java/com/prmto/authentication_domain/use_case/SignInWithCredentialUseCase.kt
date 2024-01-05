package com.prmto.authentication_domain.use_case

import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.GoogleAuthProvider
import com.prmto.authentication_domain.models.GoogleAuthenticationResult
import com.prmto.authentication_domain.repository.AuthenticationRepository
import com.prmto.core_domain.util.UiText
import javax.inject.Inject

class SignInWithCredentialUseCase @Inject constructor(
    private val repository: AuthenticationRepository
) {
    operator fun invoke(
        task: Task<GoogleSignInAccount>,
        onSuccess: () -> Unit,
        onFailure: (uiText: UiText) -> Unit
    ): GoogleAuthenticationResult {

        val account: GoogleSignInAccount = task.result
            ?: return GoogleAuthenticationResult(
                errorMessage = UiText.unknownError()
            )

        val credential = GoogleAuthProvider.getCredential(account.idToken, null)

        return GoogleAuthenticationResult(
            result = repository.signInWithCredential(
                credential = credential,
                onSuccess = onSuccess,
                onFailure = onFailure
            )
        )
    }
}