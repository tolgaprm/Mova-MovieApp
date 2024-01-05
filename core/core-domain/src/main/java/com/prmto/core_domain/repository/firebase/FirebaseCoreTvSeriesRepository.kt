package com.prmto.core_domain.repository.firebase

import com.prmto.core_domain.models.tv.TvSeries
import com.prmto.core_domain.util.UiText

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