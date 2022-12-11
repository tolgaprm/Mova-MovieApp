package com.prmto.mova_movieapp.domain.use_case.get_top_rated_tv_series

import androidx.paging.PagingData
import com.prmto.mova_movieapp.domain.models.TvSeries
import com.prmto.mova_movieapp.domain.repository.RemoteRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTopRatedTvSeriesUseCase @Inject constructor(
    private val remoteRepository: RemoteRepository
) {
    operator fun invoke(language: String): Flow<PagingData<TvSeries>> {
        return remoteRepository.getTopRatedTvs(
            language = language.lowercase()
        )
    }
}