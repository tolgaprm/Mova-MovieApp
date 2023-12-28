package com.prmto.mova_movieapp.core.domain.use_case.database.movie

import com.prmto.mova_movieapp.core.domain.models.movie.Movie
import com.prmto.mova_movieapp.core.domain.repository.local.LocalDatabaseRepository
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