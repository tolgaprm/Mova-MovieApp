package com.prmto.mova_movieapp.feature_home.domain.tv.usecases

import androidx.paging.PagingData
import androidx.paging.map
import com.prmto.mova_movieapp.core.domain.models.TvSeries
import com.prmto.mova_movieapp.core.domain.use_case.GetTvGenreListUseCase
import com.prmto.mova_movieapp.core.presentation.util.HandleUtils
import com.prmto.mova_movieapp.feature_home.domain.tv.HomeTvRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

class GetTopRatedTvSeriesUseCase @Inject constructor(
    private val homeTvRepository: HomeTvRepository,
    private val getTvGenreListUseCase: GetTvGenreListUseCase
) {
    operator fun invoke(language: String): Flow<PagingData<TvSeries>> {

        val languageLower = language.lowercase()

        return combine(
            homeTvRepository.getTopRatedTvs(language = languageLower),
            getTvGenreListUseCase(language = languageLower)
        ) { pagingData, genres ->
            pagingData.map { tv ->
                tv.copy(
                    genreByOne = HandleUtils.handleConvertingGenreListToOneGenreString(
                        genreList = genres,
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