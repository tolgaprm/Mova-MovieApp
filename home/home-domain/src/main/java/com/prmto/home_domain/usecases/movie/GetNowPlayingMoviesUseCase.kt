package com.prmto.home_domain.usecases.movie

import androidx.paging.PagingData
import com.prmto.core_domain.models.movie.Movie
import com.prmto.core_domain.use_case.movie.GetMovieGenreListUseCase
import com.prmto.core_domain.util.Constants.DEFAULT_LANGUAGE
import com.prmto.core_domain.util.Constants.DEFAULT_REGION
import com.prmto.core_domain.util.combineMovieAndGenreReturnFlow
import com.prmto.home_domain.repository.movie.HomeMovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetNowPlayingMoviesUseCase @Inject constructor(
    private val homeMovieRepository: HomeMovieRepository,
    private val getMovieGenreListUseCase: GetMovieGenreListUseCase
) {
    operator fun invoke(
        language: String = DEFAULT_LANGUAGE,
        region: String = DEFAULT_REGION
    ): Flow<PagingData<Movie>> {
        return combineMovieAndGenreReturnFlow(
            movieGenreResourceFlow = getMovieGenreListUseCase(language = language),
            moviePagingDataFlow = homeMovieRepository.getNowPlayingMovies(
                language = language,
                region = region
            )
        )
    }
}