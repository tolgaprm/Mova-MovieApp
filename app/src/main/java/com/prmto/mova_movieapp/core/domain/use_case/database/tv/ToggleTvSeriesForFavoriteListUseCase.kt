package com.prmto.mova_movieapp.core.domain.use_case.database.tv

import com.prmto.mova_movieapp.core.domain.models.tv.TvSeries
import com.prmto.mova_movieapp.core.domain.repository.local.LocalDatabaseRepository
import javax.inject.Inject

class ToggleTvSeriesForFavoriteListUseCase @Inject constructor(
    private val repository: LocalDatabaseRepository,
) {
    private val tvSeriesLocalRepository = repository.tvSeriesLocalRepository
    suspend operator fun invoke(
        tvSeries: TvSeries,
        doesAddFavoriteList: Boolean,
    ) {
        if (doesAddFavoriteList) {
            tvSeriesLocalRepository.deleteTvSeriesFromFavoriteList(tvSeries = tvSeries)
        } else {
            tvSeriesLocalRepository.insertTvSeriesToFavoriteList(tvSeries = tvSeries)
        }
    }
}