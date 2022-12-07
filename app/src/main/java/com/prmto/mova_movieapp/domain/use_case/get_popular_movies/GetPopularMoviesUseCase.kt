package com.prmto.mova_movieapp.domain.use_case.get_popular_movies

import androidx.paging.PagingData
import com.prmto.mova_movieapp.domain.models.Movie
import com.prmto.mova_movieapp.domain.repository.RemoteRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPopularMoviesUseCase @Inject constructor(
    private val remoteRepository: RemoteRepository
) {
    operator fun invoke(
        language: String,
        region: String
    ): Flow<PagingData<Movie>> {
        return remoteRepository.getPopularMovies(
            language = language,
            region = region,
        )
    }
}