package com.prmto.mova_movieapp.core.domain.use_case.database.tv

import com.prmto.mova_movieapp.core.domain.models.tv.TvSeries
import com.prmto.mova_movieapp.core.domain.repository.local.LocalDatabaseRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTvSeriesInWatchListUseCase @Inject constructor(
    private val repository: LocalDatabaseRepository
) {

    operator fun invoke(): Flow<List<TvSeries>> {
        return repository.tvSeriesLocalRepository.getTvSeriesInWatchList()
    }
}