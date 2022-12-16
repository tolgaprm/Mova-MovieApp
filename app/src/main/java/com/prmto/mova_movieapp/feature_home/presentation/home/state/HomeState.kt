package com.prmto.mova_movieapp.feature_home.presentation.home.state

import com.prmto.mova_movieapp.core.data.dto.Genre
import com.prmto.mova_movieapp.core.presentation.util.UiText

data class HomeState(
    val isShowsSeeAllPage: Boolean = false,
    val seeAllPageToolBarText: UiText? = null,
    val countryIsoCode: String = "",
    val languageIsoCode: String = "",
    val movieGenreList: List<Genre> = emptyList(),
    val tvGenreList: List<Genre> = emptyList()
)
