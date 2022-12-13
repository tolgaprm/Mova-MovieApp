package com.prmto.mova_movieapp.presentation.home

import com.prmto.mova_movieapp.presentation.util.UiText


sealed class HomeEvent {
    data class ClickSeeAllText(val seeAllPageToolBarText: UiText) : HomeEvent()
    object NavigateUpFromSeeAllSection : HomeEvent()
    data class UpdateCountryIsoCode(val countryIsoCode: String) : HomeEvent()
    object OnBackPressed : HomeEvent()
    data class NavigateToDetailBottomSheet(val directions: HomeFragmentDirections.ActionHomeFragmentToDetailBottomSheet) :
        HomeEvent()
}
