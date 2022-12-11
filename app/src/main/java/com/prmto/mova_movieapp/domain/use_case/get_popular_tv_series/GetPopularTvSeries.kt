package com.prmto.mova_movieapp.domain.use_case.get_popular_tv_series

import androidx.paging.PagingData
import com.prmto.mova_movieapp.domain.models.TvSeries
import com.prmto.mova_movieapp.domain.repository.RemoteRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPopularTvSeries @Inject constructor(
    private val remoteRepository: RemoteRepository
) {

    operator fun invoke(language: String): Flow<PagingData<TvSeries>> {
        return remoteRepository.getPopularTvs(
            language = language.lowercase()
        )
    }
}