package com.prmto.mova_movieapp.feature_movie_tv_detail.domain.use_cases

import com.prmto.mova_movieapp.R
import com.prmto.mova_movieapp.core.presentation.util.UiText
import com.prmto.mova_movieapp.core.util.Resource
import com.prmto.mova_movieapp.feature_movie_tv_detail.domain.models.detail.TvDetail
import com.prmto.mova_movieapp.feature_movie_tv_detail.domain.repository.DetailRepository
import com.prmto.mova_movieapp.feature_movie_tv_detail.domain.util.HandleUtils
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okio.IOException
import retrofit2.HttpException
import timber.log.Timber
import javax.inject.Inject

class GetTvDetailUseCase @Inject constructor(
    private val detailRepository: DetailRepository,
    private val getProviderLinksUseCase: GetProviderLinksUseCase
) {

    operator fun invoke(
        language: String,
        tvId: Int
    ): Flow<Resource<TvDetail>> {
        return flow {
            try {
                val result = detailRepository.getTvDetail(language = language, tvId = tvId)
                val tvDetail = result.copy(
                    ratingValue = HandleUtils.calculateRatingBarValue(result.voteAverage),
                    releaseDate = HandleUtils.convertTvSeriesReleaseDateBetweenFirstAndLastDate(
                        firstAirDate = result.firstAirDate,
                        lastAirDate = result.lastAirDate,
                        status = result.status
                    ),
                    watchProviders = result.watchProviders.copy(
                        results = result.watchProviders.results?.copy(
                            tr = result.watchProviders.results.tr?.copy(
                                watchProvidersLink = getProviderLinksUseCase.invoke(result.watchProviders.results.tr.link)
                            ),
                            us = result.watchProviders.results.us?.copy(
                                watchProvidersLink = getProviderLinksUseCase.invoke(result.watchProviders.results.us.link)
                            ),
                            de = result.watchProviders.results.de?.copy(
                                watchProvidersLink = getProviderLinksUseCase.invoke(result.watchProviders.results.de.link)
                            )
                        )
                    )
                )

                emit(Resource.Success(data = tvDetail))
            } catch (e: IOException) {
                emit(Resource.Error(UiText.StringResource(R.string.internet_error)))
            } catch (e: HttpException) {
                emit(Resource.Error(UiText.StringResource(R.string.unknown_error)))
            } catch (e: Exception) {
                Timber.e(e)
                emit(Resource.Error(UiText.StringResource(R.string.unknown_error)))
            }
        }
    }
}