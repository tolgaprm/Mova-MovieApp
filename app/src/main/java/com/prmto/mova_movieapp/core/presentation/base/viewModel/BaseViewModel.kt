package com.prmto.mova_movieapp.core.presentation.base.viewModel

import androidx.lifecycle.ViewModel
import com.prmto.mova_movieapp.core.presentation.util.UiText
import com.prmto.mova_movieapp.core.util.Resource
import kotlinx.coroutines.CoroutineExceptionHandler
import timber.log.Timber

abstract class BaseViewModel : ViewModel() {
    protected val handler = CoroutineExceptionHandler { _, throwable ->
        Timber.d(throwable.toString())
    }


    /**
     * Handles a resource operation with optional success and error callbacks.
     *
     * This function is designed to simplify resource handling by providing callbacks for both
     * success and error cases. It executes the `resourceSupplier` lambda to obtain the resource,
     * and then it invokes the appropriate callback based on the result.
     *
     * @param resourceSupplier A lambda that supplies the resource to be processed.
     * @param onErrorCallback A callback to be executed in case of an error, with a `UiText` parameter
     *                        representing the error message or text to be displayed.
     * @param onSuccessCallback A callback to be executed when the resource operation succeeds.
     *
     * Usage:
     * ```kotlin
     * handleResourceWithCallbacks(
     *     resourceSupplier = { fetchDataFromServer() },
     *     onErrorCallback = {message-> _uiState.update{it.copy(error= message)} },
     *     onSuccessCallback = { data ->
     *         _uiState.update{}
     *     }
     * )
     * ```
     *
     * @param T The type of the resource being handled.
     */
    protected inline fun <T> handleResourceWithCallbacks(
        resourceSupplier: () -> Resource<T>,
        crossinline onSuccessCallback: (T) -> Unit,
        crossinline onErrorCallback: (UiText) -> Unit
    ) {
        when (val response = resourceSupplier()) {
            is Resource.Success -> {
                onSuccessCallback(response.data!!)
            }

            is Resource.Error -> {
                onErrorCallback(response.uiText ?: UiText.somethingWentWrong())
            }
        }
    }
}