package com.prmto.mova_movieapp.feature_movie_tv_detail.domain.use_cases.tv

import com.prmto.mova_movieapp.core.data.util.HandleUtils
import com.prmto.mova_movieapp.core.domain.models.tv.TvSeries
import com.prmto.mova_movieapp.core.domain.use_case.tv.GetTvGenreListUseCase
import com.prmto.mova_movieapp.feature_movie_tv_detail.domain.repository.tv.TvDetailRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

class GetTvRecommendationUseCase @Inject constructor(
    private val tvDetailRepository: TvDetailRepository,
    private val getTvGenreListUseCase: GetTvGenreListUseCase
) {
    operator fun invoke(tvId: Int, language: String): Flow<List<TvSeries>> {
        val languageLower = language.lowercase()

        return combine(
            tvDetailRepository.getRecommendationsForTv(tvSeriesId = tvId, language = languageLower),
            getTvGenreListUseCase(language = languageLower)
        ) { tvSeries, genres ->
            tvSeries.map { tv ->
                tv.copy(
                    genreByOne = HandleUtils.handleConvertingGenreListToOneGenreString(
                        genreList = genres,
                        genreIds = tv.genreIds
                    )
                )
            }
        }
    }
}