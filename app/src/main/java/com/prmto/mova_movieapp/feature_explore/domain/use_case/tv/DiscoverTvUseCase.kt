package com.prmto.mova_movieapp.feature_explore.domain.use_case.tv

import androidx.paging.PagingData
import androidx.paging.map
import com.prmto.mova_movieapp.core.domain.models.TvSeries
import com.prmto.mova_movieapp.core.domain.use_case.tv.GetTvGenreListUseCase
import com.prmto.mova_movieapp.core.presentation.util.HandleUtils
import com.prmto.mova_movieapp.feature_explore.domain.repository.ExploreRepository
import com.prmto.mova_movieapp.feature_explore.presentation.filter_bottom_sheet.state.FilterBottomState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

class DiscoverTvUseCase @Inject constructor(
    private val exploreRepository: ExploreRepository,
    private val tvGenreListUseCase: GetTvGenreListUseCase
) {
    operator fun invoke(
        language: String,
        filterBottomState: FilterBottomState
    ): Flow<PagingData<TvSeries>> {
        val languageLower = language.lowercase()

        return combine(
            exploreRepository.discoverTv(
                language = languageLower,
                filterBottomState = filterBottomState
            ),
            tvGenreListUseCase(language = languageLower)
        ) { pagingData, genreList ->
            pagingData.map { tv ->
                tv.copy(
                    genreByOne = HandleUtils.handleConvertingGenreListToOneGenreString(
                        genreList = genreList,
                        genreIds = tv.genreIds
                    ),
                    voteCountByString = HandleUtils.convertingVoteCountToString(tv.voteCount),
                    firstAirDate = HandleUtils.convertToYearFromDate(
                        releaseDate = tv.firstAirDate ?: ""
                    )
                )
            }
        }
    }
}