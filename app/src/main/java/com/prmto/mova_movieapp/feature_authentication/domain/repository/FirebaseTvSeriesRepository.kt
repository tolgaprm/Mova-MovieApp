package com.prmto.mova_movieapp.feature_authentication.domain.repository

import com.prmto.mova_movieapp.core.domain.models.FavoriteTvSeries
import com.prmto.mova_movieapp.core.domain.models.TvSeriesWatchListItem
import com.prmto.mova_movieapp.core.presentation.util.UiText

interface FirebaseTvSeriesRepository {

    fun getFavoriteTvSeries(
        userUid: String,
        onSuccess: (List<FavoriteTvSeries>) -> Unit,
        onFailure: (uiText: UiText) -> Unit
    )

    fun getTvSeriesInWatchList(
        userUid: String,
        onSuccess: (List<TvSeriesWatchListItem>) -> Unit,
        onFailure: (uiText: UiText) -> Unit
    )
}