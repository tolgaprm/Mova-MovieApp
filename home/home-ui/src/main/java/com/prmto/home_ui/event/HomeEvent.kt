package com.prmto.home_ui.event

import com.prmto.core_domain.util.UiText


sealed class HomeEvent {
    data class ClickSeeAllText(val seeAllPageToolBarText: UiText) : HomeEvent()
    data class UpdateCountryIsoCode(val countryIsoCode: String) : HomeEvent()
}