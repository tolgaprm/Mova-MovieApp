package com.prmto.mova_movieapp.core.domain.repository.firebase

import com.prmto.mova_movieapp.core.domain.models.tv.TvSeries
import com.prmto.mova_movieapp.core.presentation.util.UiText

interface FirebaseCoreTvSeriesRepository {
    fun addTvSeriesToFavoriteList(
        userUid: String,
        data: Map<String, List<TvSeries>>,
        onSuccess: () -> Unit,
        onFailure: (uiText: UiText) -> Unit,

        )

    fun addTvSeriesToWatchList(
        userUid: String,
        data: Map<String, List<TvSeries>>,
        onSuccess: () -> Unit,
        onFailure: (uiText: UiText) -> Unit,
    )
}