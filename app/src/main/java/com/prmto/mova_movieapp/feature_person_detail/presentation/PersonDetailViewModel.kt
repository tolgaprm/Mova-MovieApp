package com.prmto.mova_movieapp.feature_person_detail.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.prmto.mova_movieapp.core.presentation.base.viewModel.BaseViewModelWithUiEvent
import com.prmto.mova_movieapp.core.presentation.util.UiEvent
import com.prmto.mova_movieapp.feature_person_detail.domain.use_case.PersonDetailUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PersonDetailViewModel @Inject constructor(
    private val personDetailUseCases: PersonDetailUseCases,
    savedStateHandle: SavedStateHandle
) : BaseViewModelWithUiEvent<UiEvent>() {

    private val mutableState = MutableStateFlow(PersonDetailState())
    val state = mutableState.asStateFlow()

    init {
        PersonDetailFragmentArgs.fromSavedStateHandle(savedStateHandle)
            .personId.let { personId ->
                getPersonDetail(personId = personId)
            }
    }

    private fun getLanguage(): Deferred<String> {
        return viewModelScope.async(handler) {
            personDetailUseCases.getLanguageIsoCodeUseCase().first()
        }
    }

    private fun getPersonDetail(personId: Int) {
        mutableState.update { it.copy(isLoading = true) }
        viewModelScope.launch(handler) {
            handleResourceWithCallbacks(
                resourceSupplier = {
                    val language = getLanguage().await()
                    personDetailUseCases.getPersonDetailUseCase(
                        personId = personId,
                        language = language
                    )
                },
                onErrorCallback = { uiText ->
                    mutableState.update { it.copy(isLoading = false) }
                    addConsumableViewEvent(UiEvent.ShowSnackbar(uiText))
                },
                onSuccessCallback = { personDetail ->
                    mutableState.value = PersonDetailState(personDetail = personDetail)
                }
            )
        }
    }
}