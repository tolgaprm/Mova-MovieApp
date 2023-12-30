package com.prmto.mova_movieapp.feature_home.presentation.home.event

import com.prmto.core_domain.util.UiText
import com.prmto.mova_movieapp.feature_home.presentation.home.HomeFragmentDirections


sealed class HomeEvent {
    data class ClickSeeAllText(val seeAllPageToolBarText: UiText) : HomeEvent()
    data class UpdateCountryIsoCode(val countryIsoCode: String) : HomeEvent()
    data class NavigateToDetailBottomSheet(val directions: HomeFragmentDirections.ActionHomeFragmentToDetailBottomSheet) :
        HomeEvent()
}