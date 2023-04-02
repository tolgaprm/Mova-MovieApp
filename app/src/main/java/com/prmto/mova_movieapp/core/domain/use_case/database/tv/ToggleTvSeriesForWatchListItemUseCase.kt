package com.prmto.mova_movieapp.core.domain.use_case.database.tv

import com.prmto.mova_movieapp.core.domain.models.TvSeries
import com.prmto.mova_movieapp.core.domain.repository.local.LocalDatabaseRepository
import javax.inject.Inject

class ToggleTvSeriesForWatchListItemUseCase @Inject constructor(
    private val repository: LocalDatabaseRepository
) {
    private val tvSeriesLocalRepository = repository.tvSeriesLocalRepository

    suspend operator fun invoke(
        tvSeries: TvSeries,
        doesAddWatchList: Boolean,
    ) {
        if (doesAddWatchList) {
            tvSeriesLocalRepository.deleteTvSeriesFromWatchListItem(tvSeries = tvSeries)
        } else {
            tvSeriesLocalRepository.insertTvSeriesToWatchListItem(tvSeries = tvSeries)
        }
    }
}