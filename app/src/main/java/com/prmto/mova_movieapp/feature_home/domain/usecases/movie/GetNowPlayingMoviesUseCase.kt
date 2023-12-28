package com.prmto.mova_movieapp.feature_home.domain.usecases.movie

import androidx.paging.PagingData
import com.prmto.mova_movieapp.core.domain.models.movie.Movie
import com.prmto.mova_movieapp.core.domain.use_case.movie.GetMovieGenreListUseCase
import com.prmto.mova_movieapp.core.domain.util.combineMovieAndGenreReturnFlow
import com.prmto.mova_movieapp.core.util.Constants.DEFAULT_LANGUAGE
import com.prmto.mova_movieapp.core.util.Constants.DEFAULT_REGION
import com.prmto.mova_movieapp.feature_home.domain.repository.movie.HomeMovieRepository
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