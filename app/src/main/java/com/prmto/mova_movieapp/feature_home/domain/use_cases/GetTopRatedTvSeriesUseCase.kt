package com.prmto.mova_movieapp.feature_home.domain.use_cases

import androidx.paging.PagingData
import com.prmto.mova_movieapp.feature_home.domain.models.TvSeries
import com.prmto.mova_movieapp.feature_home.domain.repository.HomeRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTopRatedTvSeriesUseCase @Inject constructor(
    private val homeRepository: HomeRepository
) {
    operator fun invoke(language: String): Flow<PagingData<TvSeries>> {
        return homeRepository.getTopRatedTvs(
            language = language.lowercase()
        )
    }
}