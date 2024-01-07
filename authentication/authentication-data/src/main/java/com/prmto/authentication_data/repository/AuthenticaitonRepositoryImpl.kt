package com.prmto.authentication_data.repository

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.prmto.authentication_domain.repository.AuthenticationRepository
import com.prmto.authentication_domain.util.FirebaseAuthMessage
import com.prmto.core_data.util.returnResourceByTaskResult
import com.prmto.core_data.util.safeCallWithForFirebase
import com.prmto.core_domain.util.Resource
import com.prmto.core_domain.util.SimpleResource
import com.prmto.core_domain.util.UiText
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthenticationRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth
) : AuthenticationRepository {

    override suspend fun signInWithEmailAndPassword(
        email: String,
        password: String
    ): SimpleResource {
        return safeCallWithForFirebase(
            setFirebaseAuthErrorMessage = ::setException
        ) {
            val task = firebaseAuth.signInWithEmailAndPassword(email, password)
            task.await()
            return task.returnResourceByTaskResult(Unit)
        }
    }

    override suspend fun createWithEmailAndPassword(
        email: String,
        password: String
    ): SimpleResource {
        return safeCallWithForFirebase(
            setFirebaseAuthErrorMessage = ::setException
        ) {
            val task = firebaseAuth.createUserWithEmailAndPassword(
                email, password
            )
            task.await()
            return task.returnResourceByTaskResult(Unit)
        }
    }

    override suspend fun sendPasswordResetEmail(email: String): SimpleResource {
        return safeCallWithForFirebase(
            setFirebaseAuthErrorMessage = ::setException
        ) {
            val task = firebaseAuth.sendPasswordResetEmail(email)
            task.await()
            return task.returnResourceByTaskResult(Unit)
        }
    }

    override suspend fun signInWithCredential(credential: AuthCredential): SimpleResource {
        return safeCallWithForFirebase(
            setFirebaseAuthErrorMessage = ::setException
        ) {
            val task = firebaseAuth.signInWithCredential(credential)
            task.await()
            return task.returnResourceByTaskResult(Unit)
        }
    }

    private fun setException(
        exception: FirebaseAuthException
    ): SimpleResource {
        val errorCode = exception.errorCode
        val errorStringId = FirebaseAuthMessage.getMessage(errorCode = errorCode)
        return Resource.Error(UiText.StringResource(errorStringId))
    }

    private fun <T> Task<AuthResult>.returnResourceByTaskResult(successItem: T): Resource<T> {
        return if (isSuccessful) {
            Resource.Success(successItem)
        } else {
            Resource.Error(UiText.unknownError())
        }
    }
}