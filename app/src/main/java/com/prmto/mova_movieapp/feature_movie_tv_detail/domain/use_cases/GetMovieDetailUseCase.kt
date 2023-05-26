package com.prmto.mova_movieapp.feature_movie_tv_detail.domain.use_cases

import com.prmto.mova_movieapp.R
import com.prmto.mova_movieapp.core.presentation.util.UiText
import com.prmto.mova_movieapp.core.util.Resource
import com.prmto.mova_movieapp.feature_movie_tv_detail.domain.models.detail.MovieDetail
import com.prmto.mova_movieapp.feature_movie_tv_detail.domain.repository.DetailRepository
import com.prmto.mova_movieapp.feature_movie_tv_detail.domain.util.HandleUtils
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okio.IOException
import retrofit2.HttpException
import timber.log.Timber
import javax.inject.Inject

class GetMovieDetailUseCase @Inject constructor(
    private val detailRepository: DetailRepository,
    private val getProviderLinksUseCase: GetProviderLinksUseCase
) {
    operator fun invoke(
        language: String,
        movieId: Int,
    ): Flow<Resource<MovieDetail>> {
        return flow {
            try {
                val result =
                    detailRepository.getMovieDetail(language = language, movieId = movieId)

                val movieDetail = result.copy(
                    ratingValue = HandleUtils.calculateRatingBarValue(result.voteAverage),
                    convertedRuntime = HandleUtils.convertRuntimeAsHourAndMinutes(result.runtime),
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

                emit(Resource.Success(movieDetail))

            } catch (e: IOException) {
                emit(Resource.Error(UiText.StringResource(R.string.internet_error)))
            } catch (e: HttpException) {
                emit(Resource.Error(UiText.StringResource(R.string.oops_something_went_wrong)))
            } catch (e: Exception) {
                Timber.e(e)
                emit(Resource.Error(UiText.StringResource(R.string.oops_something_went_wrong)))
            }
        }
    }
}