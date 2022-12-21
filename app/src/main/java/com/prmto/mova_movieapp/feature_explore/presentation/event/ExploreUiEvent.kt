package com.prmto.mova_movieapp.feature_explore.presentation.event

import androidx.navigation.NavDirections
import com.prmto.mova_movieapp.core.presentation.util.UiText

sealed class ExploreUiEvent {
    data class NavigateTo(val directions: NavDirections) : ExploreUiEvent()
    object PopBackStack : ExploreUiEvent()
    data class ShowSnackbar(val uiText: UiText) : ExploreUiEvent()
}