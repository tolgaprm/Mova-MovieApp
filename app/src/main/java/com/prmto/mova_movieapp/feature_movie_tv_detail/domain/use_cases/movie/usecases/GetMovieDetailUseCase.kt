package com.prmto.mova_movieapp.feature_movie_tv_detail.domain.use_cases.movie.usecases

import com.prmto.mova_movieapp.core.domain.use_case.database.movie.GetFavoriteMovieIdsUseCase
import com.prmto.mova_movieapp.core.domain.use_case.movie.GetMovieWatchListItemIdsUseCase
import com.prmto.mova_movieapp.core.domain.util.Resource
import com.prmto.mova_movieapp.core.domain.util.handleResource
import com.prmto.mova_movieapp.core.presentation.countryCode.CountryCodeProvider
import com.prmto.mova_movieapp.feature_movie_tv_detail.domain.models.detail.movie.MovieDetail
import com.prmto.mova_movieapp.feature_movie_tv_detail.domain.repository.movie.MovieDetailRepository
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class GetMovieDetailUseCase @Inject constructor(
    private val movieDetailRepository: MovieDetailRepository,
    private val countryCodeProvider: CountryCodeProvider,
    private val getFavoriteMovieIdsUseCase: GetFavoriteMovieIdsUseCase,
    private val getWatchListMovieIdsUseCase: GetMovieWatchListItemIdsUseCase
) {
    suspend operator fun invoke(
        language: String,
        movieId: Int
    ): Resource<MovieDetail> {
        return combine(
            getFavoriteMovieIdsUseCase(),
            getWatchListMovieIdsUseCase()
        ) { favoriteMovieIds, watchListMovieIds ->
            handleResource(
                resourceSupplier = {
                    movieDetailRepository.getMovieDetail(
                        language = language,
                        movieId = movieId,
                        countryIsoCode = countryCodeProvider.getCountryIsoCode()
                    )
                },
                mapper = {
                    it.copy(
                        isFavorite = favoriteMovieIds.contains(it.id),
                        isWatchList = watchListMovieIds.contains(it.id)
                    )
                }
            )
        }.first()
    }
}