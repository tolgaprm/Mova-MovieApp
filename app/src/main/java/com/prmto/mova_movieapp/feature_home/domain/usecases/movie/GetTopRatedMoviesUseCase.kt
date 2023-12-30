package com.prmto.mova_movieapp.feature_home.domain.usecases.movie

import androidx.paging.PagingData
import com.prmto.core_domain.models.movie.Movie
import com.prmto.core_domain.use_case.movie.GetMovieGenreListUseCase
import com.prmto.core_domain.util.combineMovieAndGenreMapOneGenre
import com.prmto.mova_movieapp.feature_home.domain.repository.movie.HomeMovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTopRatedMoviesUseCase @Inject constructor(
    private val homeMovieRepository: HomeMovieRepository,
    private val getMovieGenreListUseCase: GetMovieGenreListUseCase
) {
    operator fun invoke(
        language: String,
        region: String
    ): Flow<PagingData<Movie>> {
        return combineMovieAndGenreMapOneGenre(
            movieGenreResourceFlow = getMovieGenreListUseCase(language = language),
            moviePagingDataFlow = homeMovieRepository.getTopRatedMovies(
                language = language,
                region = region
            )
        )
    }
}