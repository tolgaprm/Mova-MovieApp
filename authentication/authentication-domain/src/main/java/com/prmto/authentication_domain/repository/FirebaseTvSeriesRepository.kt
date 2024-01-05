package com.prmto.authentication_domain.repository

import com.prmto.core_domain.models.tv.TvSeries
import com.prmto.core_domain.util.UiText

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