package com.prmto.mova_movieapp.feature_explore.domain.use_case

import androidx.paging.PagingData
import com.prmto.mova_movieapp.feature_explore.domain.repository.ExploreRepository
import com.prmto.mova_movieapp.feature_explore.presentation.filter_bottom_sheet.state.FilterBottomState
import com.prmto.mova_movieapp.feature_home.domain.models.TvSeries
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DiscoverTvUseCase @Inject constructor(
    private val exploreRepository: ExploreRepository
) {

    operator fun invoke(
        language: String,
        filterBottomState: FilterBottomState
    ): Flow<PagingData<TvSeries>> {
        return exploreRepository.discoverTv(
            language = language,
            filterBottomState = filterBottomState
        )
    }
}