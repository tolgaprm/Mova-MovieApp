package com.prmto.mova_movieapp.core.domain.repository

import com.prmto.mova_movieapp.core.domain.models.FavoriteTvSeries
import com.prmto.mova_movieapp.core.domain.models.TvSeriesWatchListItem
import com.prmto.mova_movieapp.core.presentation.util.UiText

interface FirebaseCoreTvSeriesRepository {
    fun addTvSeriesToFavoriteList(
        userUid: String,
        data: Map<String, List<FavoriteTvSeries>>,
        onSuccess: () -> Unit,
        onFailure: (uiText: UiText) -> Unit

    )

    fun addTvSeriesToWatchList(
        userUid: String,
        data: Map<String, List<TvSeriesWatchListItem>>,
        onSuccess: () -> Unit,
        onFailure: (uiText: UiText) -> Unit
    )
}