package com.prmto.mova_movieapp.feature_movie_tv_detail.domain.use_cases.tv

import com.prmto.mova_movieapp.core.domain.countryCode.CountryCodeProvider
import com.prmto.mova_movieapp.core.domain.use_case.database.tv.GetFavoriteTvSeriesIdsUseCase
import com.prmto.mova_movieapp.core.domain.use_case.database.tv.GetTvSeriesWatchListItemIdsUseCase
import com.prmto.mova_movieapp.core.domain.util.handleResource
import com.prmto.mova_movieapp.core.util.Resource
import com.prmto.mova_movieapp.feature_movie_tv_detail.domain.models.detail.tv.TvDetail
import com.prmto.mova_movieapp.feature_movie_tv_detail.domain.repository.tv.TvDetailRepository
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class GetTvDetailUseCase @Inject constructor(
    private val tvDetailRepository: TvDetailRepository,
    private val countryCodeProvider: CountryCodeProvider,
    private val getFavoriteTvSeriesIdsUseCase: GetFavoriteTvSeriesIdsUseCase,
    private val getTvSeriesWatchListItemIdsUseCase: GetTvSeriesWatchListItemIdsUseCase
) {

    suspend operator fun invoke(
        language: String,
        tvId: Int
    ): Resource<TvDetail> {

        return combine(
            getFavoriteTvSeriesIdsUseCase(),
            getTvSeriesWatchListItemIdsUseCase()
        ) { favoriteTvIds, tvWatchListIds ->
            handleResource(
                resourceSupplier = {
                    tvDetailRepository.getTvDetail(
                        language = language,
                        tvSeriesId = tvId,
                        countryIsoCode = countryCodeProvider.getCountryIsoCode()
                    )
                },
                mapper = { tvDetail ->
                    tvDetail.copy(
                        isFavorite = favoriteTvIds.contains(tvDetail.id),
                        isWatchList = tvWatchListIds.contains(tvDetail.id)
                    )
                }
            )
        }.first()
    }
}