package com.prmto.mova_movieapp.feature_authentication.domain.repository


import com.prmto.mova_movieapp.core.domain.models.TvSeries
import com.prmto.mova_movieapp.core.presentation.util.UiText

interface FirebaseTvSeriesRepository {

    fun getFavoriteTvSeries(
        userUid: String,
        onSuccess: (List<TvSeries>) -> Unit,
        onFailure: (uiText: UiText) -> Unit,
    )

    fun getTvSeriesInWatchList(
        userUid: String,
        onSuccess: (List<TvSeries>) -> Unit,
        onFailure: (uiText: UiText) -> Unit,
    )
}