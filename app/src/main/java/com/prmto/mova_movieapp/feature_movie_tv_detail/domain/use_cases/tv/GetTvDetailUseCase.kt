package com.prmto.mova_movieapp.feature_movie_tv_detail.domain.use_cases.tv

import com.prmto.mova_movieapp.core.domain.countryCode.CountryCodeProvider
import com.prmto.mova_movieapp.core.presentation.util.UiText
import com.prmto.mova_movieapp.core.util.Resource
import com.prmto.mova_movieapp.feature_movie_tv_detail.domain.models.detail.tv.TvDetail
import com.prmto.mova_movieapp.feature_movie_tv_detail.domain.repository.tv.TvDetailRepository
import com.prmto.mova_movieapp.feature_movie_tv_detail.domain.util.HandleUtils
import javax.inject.Inject

class GetTvDetailUseCase @Inject constructor(
    private val tvDetailRepository: TvDetailRepository,
    private val countryCodeProvider: CountryCodeProvider,
) {

    suspend operator fun invoke(
        language: String,
        tvId: Int
    ): Resource<TvDetail> {
        val resource = tvDetailRepository.getTvDetail(
            language = language,
            tvSeriesId = tvId,
            countryIsoCode = countryCodeProvider.getCountryIsoCode()
        )

        return when (resource) {
            is Resource.Success -> {
                resource.data?.let {
                    val result = it.copy(
                        ratingValue = HandleUtils.calculateRatingBarValue(it.voteAverage),
                        releaseDate = HandleUtils.convertTvSeriesReleaseDateBetweenFirstAndLastDate(
                            firstAirDate = it.firstAirDate,
                            lastAirDate = it.lastAirDate,
                            status = it.status
                        )
                    )
                    Resource.Success(result)
                } ?: Resource.Error(UiText.somethingWentWrong())
            }

            is Resource.Error -> {
                Resource.Error(resource.uiText ?: UiText.somethingWentWrong())
            }
        }
    }
}