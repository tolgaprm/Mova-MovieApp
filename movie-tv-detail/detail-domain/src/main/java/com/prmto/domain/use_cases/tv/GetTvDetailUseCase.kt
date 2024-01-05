package com.prmto.domain.use_cases.tv

import com.prmto.core_domain.countryCode.CountryCodeProvider
import com.prmto.core_domain.use_case.database.tv.GetFavoriteTvSeriesIdsUseCase
import com.prmto.core_domain.use_case.database.tv.GetTvSeriesWatchListItemIdsUseCase
import com.prmto.core_domain.util.Resource
import com.prmto.core_domain.util.handleResource
import com.prmto.domain.repository.tv.TvDetailRepository
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
    ): Resource<com.prmto.domain.models.detail.tv.TvDetail> {

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