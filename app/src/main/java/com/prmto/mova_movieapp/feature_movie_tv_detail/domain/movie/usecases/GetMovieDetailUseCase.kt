package com.prmto.mova_movieapp.feature_movie_tv_detail.domain.movie.usecases

import com.prmto.mova_movieapp.core.presentation.util.UiText
import com.prmto.mova_movieapp.core.util.Resource
import com.prmto.mova_movieapp.core.util.countryCode.CountryCodeProvider
import com.prmto.mova_movieapp.feature_movie_tv_detail.domain.movie.model.MovieDetail
import com.prmto.mova_movieapp.feature_movie_tv_detail.domain.movie.repository.MovieDetailRepository
import com.prmto.mova_movieapp.feature_movie_tv_detail.domain.util.HandleUtils
import javax.inject.Inject

class GetMovieDetailUseCase @Inject constructor(
    private val movieDetailRepository: MovieDetailRepository,
    private val countryCodeProvider: CountryCodeProvider,
) {
    suspend operator fun invoke(
        language: String,
        movieId: Int
    ): Resource<MovieDetail> {
        val resource =
            movieDetailRepository.getMovieDetail(
                language = language,
                movieId = movieId,
                countryIsoCode = countryCodeProvider.getCountryIsoCode()
            )

        return when (resource) {
            is Resource.Success -> {
                resource.data?.let {
                    val result = it.copy(
                        ratingValue = HandleUtils.calculateRatingBarValue(it.voteAverage),
                        convertedRuntime = HandleUtils.convertRuntimeAsHourAndMinutes(it.runtime)
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