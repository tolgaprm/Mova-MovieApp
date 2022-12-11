package com.prmto.mova_movieapp.domain.use_case.get_top_rated_movies

import androidx.paging.PagingData
import com.prmto.mova_movieapp.domain.models.Movie
import com.prmto.mova_movieapp.domain.repository.RemoteRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTopRatedMoviesUseCase @Inject constructor(
    private val repository: RemoteRepository
) {

    operator fun invoke(
        language: String,
        region: String
    ): Flow<PagingData<Movie>> {
        return repository.getTopRatedMovies(
            language = language.lowercase(),
            region = region
        )
    }
}