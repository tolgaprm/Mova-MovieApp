package com.prmto.mova_movieapp.feature_explore.domain.use_case

import androidx.paging.PagingData
import androidx.paging.map
import com.prmto.mova_movieapp.core.domain.use_case.GetMovieGenreListUseCase
import com.prmto.mova_movieapp.core.domain.use_case.GetTvGenreListUseCase
import com.prmto.mova_movieapp.core.presentation.util.HandleUtils
import com.prmto.mova_movieapp.feature_explore.data.dto.SearchDto
import com.prmto.mova_movieapp.feature_explore.domain.repository.ExploreRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

class MultiSearchUseCase @Inject constructor(
    private val repository: ExploreRepository,
    private val getMovieGenreListUseCase: GetMovieGenreListUseCase,
    private val getTvGenreListUseCase: GetTvGenreListUseCase
) {
    operator fun invoke(
        query: String,
        language: String
    ): Flow<PagingData<SearchDto>> {
        val languageLower = language.lowercase()
        return combine(
            repository.multiSearch(query = query, language = languageLower),
            getMovieGenreListUseCase(language = languageLower),
            getTvGenreListUseCase(language = languageLower)
        ) { pagingData, movieGenres, tvGenres ->
            pagingData.map { searchDto ->
                searchDto.copy(
                    genreByOneForMovie = HandleUtils.handleConvertingGenreListToOneGenreString(
                        genreList = movieGenres, genreIds = searchDto.genreIds ?: emptyList()
                    ),
                    genreByOneForTv = HandleUtils.handleConvertingGenreListToOneGenreString(
                        genreList = tvGenres, genreIds = searchDto.genreIds ?: emptyList()
                    ),
                    voteCountByString = HandleUtils.convertingVoteCountToString(
                        searchDto.voteCount ?: 0
                    ),
                    releaseDate = HandleUtils.convertToYearFromDate(searchDto.releaseDate ?: ""),
                    firstAirDate = HandleUtils.convertToYearFromDate(searchDto.firstAirDate ?: "")
                )
            }
        }

    }
}