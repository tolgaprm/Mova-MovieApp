package com.prmto.mova_movieapp.feature_home.presentation.home.state

import com.prmto.core_domain.models.genre.Genre
import com.prmto.core_domain.util.UiText

data class HomeState(
    val isShowsSeeAllPage: Boolean = false,
    val seeAllPageToolBarText: UiText? = null,
    val countryIsoCode: String = "",
    val languageIsoCode: String = "",
    val movieGenreList: List<Genre> = emptyList(),
    val tvGenreList: List<Genre> = emptyList(),
)