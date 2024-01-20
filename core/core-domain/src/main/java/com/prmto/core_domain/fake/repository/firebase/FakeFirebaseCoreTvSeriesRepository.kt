package com.prmto.core_domain.fake.repository.firebase

import com.prmto.core_domain.models.tv.TvSeries
import com.prmto.core_domain.repository.firebase.FirebaseCoreTvSeriesRepository
import com.prmto.core_domain.util.Resource
import com.prmto.core_domain.util.SimpleResource
import com.prmto.core_domain.util.UiText

class FakeFirebaseCoreTvSeriesRepository : FirebaseCoreTvSeriesRepository {
    private var isReturnError = false

    override suspend fun addTvSeriesToFavoriteList(
        userUid: String,
        tvSeriesInFavoriteList: List<TvSeries>
    ): SimpleResource {
        return if (isReturnError) {
            Resource.Error((UiText.somethingWentWrong()))
        } else {
            Resource.Success(Unit)
        }
    }

    override suspend fun addTvSeriesToWatchList(
        userUid: String,
        tvSeriesInWatchList: List<TvSeries>
    ): SimpleResource {
        return if (isReturnError) {
            Resource.Error((UiText.somethingWentWrong()))
        } else {
            Resource.Success(Unit)
        }
    }
}