package com.prmto.mova_movieapp.presentation.home

import com.prmto.mova_movieapp.data.models.Genre
import com.prmto.mova_movieapp.presentation.util.UiText

data class HomeState(
    val isShowsSeeAllPage: Boolean = false,
    val seeAllPageToolBarText: UiText? = null,
    val countryIsoCode: String = "",
    val languageIsoCode: String = "",
    val movieGenreList: List<Genre> = emptyList(),
    val tvGenreList: List<Genre> = emptyList()
)
