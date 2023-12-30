package com.prmto.core_domain.use_case.database.movie

import com.prmto.core_domain.models.movie.Movie
import com.prmto.core_domain.repository.local.LocalDatabaseRepository
import javax.inject.Inject

class ToggleMovieForFavoriteListUseCase @Inject constructor(
    private val repository: LocalDatabaseRepository,
) {

    private val movieLocalRepository = repository.movieLocalRepository
    suspend operator fun invoke(
        movie: Movie,
        doesAddFavoriteList: Boolean,
    ) {
        if (doesAddFavoriteList) {
            movieLocalRepository.deleteMovieFromFavoriteList(
                movie = movie
            )
        } else {
            movieLocalRepository.insertMovieToFavoriteList(
                movie = movie
            )
        }
    }
}