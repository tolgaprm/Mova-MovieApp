package com.prmto.domain.use_cases.movie.usecases

import com.prmto.core_domain.countryCode.CountryCodeProvider
import com.prmto.core_domain.use_case.database.movie.GetFavoriteMovieIdsUseCase
import com.prmto.core_domain.use_case.movie.GetMovieWatchListItemIdsUseCase
import com.prmto.core_domain.util.Resource
import com.prmto.core_domain.util.handleResource
import com.prmto.domain.repository.movie.MovieDetailRepository
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
    ): Resource<com.prmto.domain.models.detail.movie.MovieDetail> {
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