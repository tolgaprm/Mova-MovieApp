package com.prmto.home_domain.usecases.tv

import androidx.paging.PagingData
import com.prmto.core_domain.models.tv.TvSeries
import com.prmto.core_domain.use_case.tv.GetTvGenreListUseCase
import com.prmto.core_domain.util.combineTvAndGenreMapOneGenre
import com.prmto.home_domain.repository.tv.HomeTvRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPopularTvSeriesUseCase @Inject constructor(
    private val homeTvRepository: HomeTvRepository,
    private val getTvGenreListUseCase: GetTvGenreListUseCase
) {
    operator fun invoke(language: String): Flow<PagingData<TvSeries>> {
        return combineTvAndGenreMapOneGenre(
            tvGenreResourceFlow = getTvGenreListUseCase(language = language),
            tvPagingDataFlow = homeTvRepository.getPopularTvs(language = language)
        )
    }
}