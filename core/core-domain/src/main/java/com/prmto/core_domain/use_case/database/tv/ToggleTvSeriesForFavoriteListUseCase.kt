package com.prmto.core_domain.use_case.database.tv

import com.prmto.core_domain.models.tv.TvSeries
import com.prmto.core_domain.repository.local.LocalDatabaseRepository
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