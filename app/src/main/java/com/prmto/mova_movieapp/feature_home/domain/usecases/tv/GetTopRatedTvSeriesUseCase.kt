package com.prmto.mova_movieapp.feature_home.domain.usecases.tv

import androidx.paging.PagingData
import com.prmto.mova_movieapp.core.domain.models.tv.TvSeries
import com.prmto.mova_movieapp.core.domain.use_case.tv.GetTvGenreListUseCase
import com.prmto.mova_movieapp.core.domain.util.combineTvAndGenreMapOneGenre
import com.prmto.mova_movieapp.feature_home.domain.repository.tv.HomeTvRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTopRatedTvSeriesUseCase @Inject constructor(
    private val homeTvRepository: HomeTvRepository,
    private val getTvGenreListUseCase: GetTvGenreListUseCase
) {
    operator fun invoke(language: String): Flow<PagingData<TvSeries>> {
        return combineTvAndGenreMapOneGenre(
            tvGenreResourceFlow = getTvGenreListUseCase(language = language),
            tvPagingDataFlow = homeTvRepository.getTopRatedTvs(language = language)
        )
    }
}